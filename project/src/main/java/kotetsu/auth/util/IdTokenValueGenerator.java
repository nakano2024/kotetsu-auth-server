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
            .claim("profile", profile)
            .issuedAt(idTokenMeta.getDuration().getIssuedAt().getValue())
            .expiration(idTokenMeta.getDuration().getExpiredAt().getValue())
            .signWith(privateKey.getKey(), Jwts.SIG.RS256)
            .compact();
        
        return IdTokenValue.of(tokenValue);
    }

    public OidcPrivateKey getRandomSelectedPrivateKey()
    {
        try { 
            final String privateKeysJson = System.getenv("OIDC_PRIVATE_KEYS");

            if (privateKeysJson == null) {
                throw new IllegalArgumentException("環境変数からの秘密鍵取得に失敗しました。");
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
      
            // PEMデータを読み込む
            PemReader pemReader = new PemReader(new StringReader(pem));
            PemObject pemObject = pemReader.readPemObject();
            pemReader.close();
            
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
        catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        catch(InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
