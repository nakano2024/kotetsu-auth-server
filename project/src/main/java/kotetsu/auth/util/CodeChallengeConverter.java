package kotetsu.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IConvertToCodeChallengePort;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;
import kotetsu.auth.exception.Sha256ConvertRuntimeException;

@Component
public class CodeChallengeConverter implements IConvertToCodeChallengePort {
    @Override
    public AuthorizationCodeChallenge convert(AuthorizationCodeVerifier codeVerifier) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] sha256Bytes = messageDigest.digest(codeVerifier.getValue().getBytes(StandardCharsets.UTF_8)); 
            final String codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Bytes);
            return AuthorizationCodeChallenge.of(codeChallenge);
        }
        catch(NoSuchAlgorithmException e) {
            throw new Sha256ConvertRuntimeException();
        }
    }
}
