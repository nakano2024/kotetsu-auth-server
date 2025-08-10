package kotetsu.auth.application.dto.input;

import lombok.Getter;

public class CheckAccessTokenInput {
    @Getter
    private final String token;

    @Getter
    private final String clientKey;

    private CheckAccessTokenInput(final String token, final String clientKey) {
        this.token = token;
        this.clientKey = clientKey;
    }

    public static CheckAccessTokenInput of(final String token, final String clientKey) {
        final CheckAccessTokenInput input = new CheckAccessTokenInput(token, clientKey);

        return input;
    }
}
