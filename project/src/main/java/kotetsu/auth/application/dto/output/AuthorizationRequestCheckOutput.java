package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class AuthorizationRequestCheckOutput {
    public static final String STATUS_OK = "ok";
    public static final String STATUS_CLIENT_NOT_FOUND = "client_not_found";
    public static final String STATUS_INVALID_REDIRECT_URI = "invalid_redirect_uri";
    public static final String STATUS_INVALID_SCOPE = "invalid_scope";

    @Getter
    private final String status;

    private AuthorizationRequestCheckOutput(final String status) {
        this.status = status;
    }

    public static AuthorizationRequestCheckOutput of(final String status) {
        final AuthorizationRequestCheckOutput output = new AuthorizationRequestCheckOutput(status);

        return output;
    }
}
