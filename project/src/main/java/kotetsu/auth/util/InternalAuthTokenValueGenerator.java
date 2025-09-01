package kotetsu.auth.util;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Map;

import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.util.IGenerateInternalAuthTokenValudPort;
import kotetsu.auth.application.domain.value.InternalAuthTokenValue;

@Component
public class InternalAuthTokenValueGenerator implements IGenerateInternalAuthTokenValudPort {
    @Override
    public InternalAuthTokenValue generate(final PendingInternalAuthToken pendingInternalAuthToken) {
        PrivateKey privateKey = getPrivateKey();

        final Map<String, Object> profile = Map.of(
            "email", pendingInternalAuthToken.getProfile().getEmail().getValue(),
            "name", pendingInternalAuthToken.getProfile().getName().getValue(),
            "image_url", pendingInternalAuthToken.getProfile().getImageUrl().getValue()
        );

        final String tokenValue = Jwts.builder()
            .subject(pendingInternalAuthToken.getSubject().getValue())
            .claim("profile", profile)
            .issuedAt(pendingInternalAuthToken.getDuration().getIssuedAt().getValue())
            .expiration(pendingInternalAuthToken.getDuration().getExpiredAt().getValue())
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact();
        
        return InternalAuthTokenValue.of(tokenValue);
    }

    public PrivateKey getPrivateKey()
    {
        final String pem = System.getenv("INTERNAL_AUTH_PUBLIC_KEY");

        if (pem == null || pem.isEmpty()) {
            throw new IllegalArgumentException("JWT_PRIVATE_KEYの値が存在しません。");
        }

        if (!pem.startsWith("-----BEGIN RSA PRIVATE KEY-----") || !pem.endsWith("-----END RSA PRIVATE KEY-----")) {
            throw new IllegalArgumentException("JWT_PRIVATE_KEYの値が不正です。");
        }

        try {       
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
            return keyFactory.generatePrivate(keySpec);
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
