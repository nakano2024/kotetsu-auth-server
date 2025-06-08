package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class AuthorizationCodeOutput {
    @Getter
    private final String code;

    private AuthorizationCodeOutput(final String code) {
        this.code = code;
    }

    public static AuthorizationCodeOutput of(final String code) {
        return new AuthorizationCodeOutput(code);
    }
}
