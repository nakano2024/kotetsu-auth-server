package kotetsu.auth.util;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;

@Component
public class PrivateKeyGetter {
    public PrivateKey getPrivateKey()
    {
        final String pem = System.getenv("JWT_PRIVATE_KEY");

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
