package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;

public interface IConvertToCodeChallengePort {
    AuthorizationCodeChallenge convert(AuthorizationCodeVerifier codeVerifier);
}
