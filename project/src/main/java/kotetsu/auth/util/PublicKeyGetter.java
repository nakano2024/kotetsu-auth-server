package kotetsu.auth.util;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;

@Component
public class PublicKeyGetter {
    public PublicKey getPublicKey() {

        final String pem = System.getenv("JWT_PUBLIC_KEY");

        if (pem == null || pem.isEmpty()) {
            throw new IllegalArgumentException("JWT_PUBLIC_KEYの値が存在しません。");
        }

        if (!pem.startsWith("-----BEGIN RSA PUBLIC KEY-----") || !pem.endsWith("-----END RSA PUBLIC KEY-----")) {
            throw new IllegalArgumentException("JWT_PUBLIC_KEYの値が不正です。");
        }

        try {       
            // PEMデータを読み込む
            PemReader pemReader = new PemReader(new StringReader(pem));
            PemObject pemObject = pemReader.readPemObject();
            pemReader.close();
            
            if (pemObject == null || !pemObject.getType().equals("RSA PUBLIC KEY")) {
                throw new IllegalArgumentException("PEMオブジェクトがRSA公開鍵ではありません");
            }

            byte[] keyBytes = pemObject.getContent();

            RSAPublicKey rsaPublicKey = RSAPublicKey.getInstance(keyBytes);

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(
                rsaPublicKey.getModulus(),
                rsaPublicKey.getPublicExponent()
            );

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
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
