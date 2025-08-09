package kotetsu.auth.application.domain.service;

import kotetsu.auth.application.domain.util.IConvertToCodeChallengePort;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;

public class CheckCodeVerifilerService {
    private final IConvertToCodeChallengePort convertToCodeChallengePort;

    public CheckCodeVerifilerService(final IConvertToCodeChallengePort convertToCodeChallengePort) {
        this.convertToCodeChallengePort = convertToCodeChallengePort;
    }
    
    public boolean isValid(final AuthorizationCodeVerifier inputCodeVerifier, final AuthorizationCode authorizationCode) {
        AuthorizationCodeChallenge convertedCodeChallenge = convertToCodeChallengePort.convert(inputCodeVerifier);

        return authorizationCode.getChallenge().equals(convertedCodeChallenge);
    }
}
