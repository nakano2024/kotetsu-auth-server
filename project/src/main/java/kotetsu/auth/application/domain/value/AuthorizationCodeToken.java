package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class AuthorizationCodeToken {
    public static final int LENGTH = 32;

    @Getter
    private final String value;

    private AuthorizationCodeToken(final String value) {
        this.value = value;
    }

    public static AuthorizationCodeToken of(final String value) {
        final AuthorizationCodeToken authorizationCodeToken = new AuthorizationCodeToken(value);
        return authorizationCodeToken;
    }
}
