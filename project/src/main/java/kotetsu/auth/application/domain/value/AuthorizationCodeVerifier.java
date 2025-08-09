package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AuthorizationCodeVerifier {
    @Getter
    @NotBlank
    private final String value;

    private AuthorizationCodeVerifier(final String value) {
        this.value = value;
    }

    public static AuthorizationCodeVerifier of(final String value) {
        final AuthorizationCodeVerifier codeVerifier = new AuthorizationCodeVerifier(value);
        return codeVerifier;
    }
}
