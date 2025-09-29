package kotetsu.auth.util;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.util.IGenerateIdTokenValuePort;
import kotetsu.auth.application.domain.value.IdTokenValue;
import kotetsu.auth.dto.util.OidcPrivateKey;
import kotetsu.auth.dto.util.OidcPrivateKeyPemJson;
import kotetsu.auth.dto.util.OidcPrivateKeyPemJsonWrapper;

@Component
public class IdTokenValueGenerator implements IGenerateIdTokenValuePort {
    @Value("${app.oidc.private.keys}")
    private String privateKeysJson;

    @Override
    public IdTokenValue generate(final IssuedIdTokenMeta idTokenMeta, final ExistingIdTokenCore idTokenCore) {
        final OidcPrivateKey privateKey = getRandomSelectedPrivateKey();

        final Map<String, Object> profile = Map.of(
            "email", idTokenCore.getProfile().getEmail().getValue(),
            "name", idTokenCore.getProfile().getName().getValue(),
            "image_url", idTokenCore.getProfile().getImageUrl().getValue()
        );

        final String tokenValue = Jwts.builder()
            .header().keyId(privateKey.getKid())
            .and().subject(idTokenCore.getSubject().getValue())
            .issuer(idTokenCore.getIssuer().getValue())
            .audience().add(idTokenCore.getAudience().getValue()).and()
            .claim("profile", profile)
            .claim("nonce", idTokenCore.getNonce().getValue())
            .issuedAt(idTokenMeta.getDuration().getIssuedAt().getValue())
            .expiration(idTokenMeta.getDuration().getExpiredAt().getValue())
            .signWith(privateKey.getKey(), Jwts.SIG.RS256)
            .compact();
        
        return IdTokenValue.of(tokenValue);
    }

    public OidcPrivateKey getRandomSelectedPrivateKey()
    {
        try { 
            if (privateKeysJson == null) {
                throw new IllegalArgumentException("秘密鍵取得に失敗しました。");
            }

            final ObjectMapper objectMapper = new ObjectMapper();
            final OidcPrivateKeyPemJsonWrapper oidcPrivateKeyPemList = objectMapper.readValue(privateKeysJson, OidcPrivateKeyPemJsonWrapper.class);

            final List<OidcPrivateKeyPemJson> keys = oidcPrivateKeyPemList.getKeys();
            if (keys.isEmpty()) {
                throw new IllegalArgumentException("OIDC_PRIVATE_KEYは最低1つ必須です。");
            }

            final Random random = new Random();
            final OidcPrivateKeyPemJson key = keys.get(random.nextInt(keys.size()));
            final String pem = key.getPem();

            if (pem == null || pem.isEmpty()) {
                throw new IllegalArgumentException("OIDC_PRIVATE_KEYのPEMが存在しません。");
            }

            if (!pem.startsWith("-----BEGIN RSA PRIVATE KEY-----") || !pem.endsWith("-----END RSA PRIVATE KEY-----")) {
                throw new IllegalArgumentException("OIDC_PRIVATE_KEYのPEMの値が不正です。");
            }
      
            PemObject pemObject;
            try (PemReader pemReader = new PemReader(new StringReader(pem))) {
                pemObject = pemReader.readPemObject();
            }
            
            if (pemObject == null || !pemObject.getType().equals("RSA PRIVATE KEY")) {
                throw new IllegalArgumentException("PEMオブジェクトがRSA秘密鍵ではありません");
            }

            byte[] keyBytes = pemObject.getContent();

            RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(keyBytes);

            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(
                rsaPrivateKey.getModulus(),
                rsaPrivateKey.getPrivateExponent()
            );

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return new OidcPrivateKey(
                key.getKid(),
                keyFactory.generatePrivate(keySpec)
            );
        }
        catch(IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
