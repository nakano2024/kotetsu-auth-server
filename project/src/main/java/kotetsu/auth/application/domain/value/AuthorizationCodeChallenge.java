package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class AuthorizationCodeChallenge {
    @Getter
    private final String value;

    private AuthorizationCodeChallenge(final String value) {
        this.value = value;
    }

    public static AuthorizationCodeChallenge of(final String value) {
        final AuthorizationCodeChallenge authorizationCodeChallenge = new AuthorizationCodeChallenge(value);
        return authorizationCodeChallenge;
    }
}
