package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class ClientCheckOutput {
    public static final String STATUS_OK = "ok";
    public static final String STATUS_CLIENT_NOT_FOUND = "client_not_found";
    public static final String STATUS_INVALID_REDIRECT_URI = "invalid_redirect_uri";

    @Getter
    private final String status;

    private ClientCheckOutput(final String status) {
        this.status = status;
    }

    public static ClientCheckOutput of(final String status) {
        return new ClientCheckOutput(status);
    }
}
