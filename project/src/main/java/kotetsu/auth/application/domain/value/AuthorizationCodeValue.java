package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class AuthorizationCodeValue {
    public static final int LENGTH = 32;

    @Getter
    private final String value;

    private AuthorizationCodeValue(final String value) {
        this.value = value;
    }

    public static AuthorizationCodeValue of(final String value) {
        final AuthorizationCodeValue authorizationCodeValue = new AuthorizationCodeValue(value);
        return authorizationCodeValue;
    }
}
