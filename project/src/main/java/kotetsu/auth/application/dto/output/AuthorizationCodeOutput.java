package kotetsu.auth.application.dto.output;

import lombok.Getter;
import lombok.Value;

@Value
public class AuthorizationCodeOutput {
    @Getter
    private final String code;

    @Getter
    private final String redirectUri;

    private AuthorizationCodeOutput(final String code, final String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public static AuthorizationCodeOutput of(final String code, final String redirectUri) {
        return new AuthorizationCodeOutput(
            code,
            redirectUri
        );
    }
}
