package kotetsu.auth.application.dto.input;

import lombok.Getter;

public class CheckAccessTokenInput {
    @Getter
    private final String token;

    private CheckAccessTokenInput(final String token) {
        this.token = token;
    }

    public static CheckAccessTokenInput of(final String token) {
        final CheckAccessTokenInput input = new CheckAccessTokenInput(token);

        return input;
    }
}
