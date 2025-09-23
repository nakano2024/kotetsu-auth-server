package kotetsu.auth.application.domain.service;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IConvertToCodeChallengePort;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;

@Component
public class CheckCodeVerifilerService {
    private final IConvertToCodeChallengePort convertToCodeChallengePort;

    public CheckCodeVerifilerService(final IConvertToCodeChallengePort convertToCodeChallengePort) {
        this.convertToCodeChallengePort = convertToCodeChallengePort;
    }
    
    public boolean isValid(final AuthorizationCodeVerifier inputCodeVerifier, final AuthorizationCodeChallenge existingCodeChallenge) {
        AuthorizationCodeChallenge convertedCodeChallenge = convertToCodeChallengePort.convert(inputCodeVerifier);

        return existingCodeChallenge.equals(convertedCodeChallenge);
    }
}
