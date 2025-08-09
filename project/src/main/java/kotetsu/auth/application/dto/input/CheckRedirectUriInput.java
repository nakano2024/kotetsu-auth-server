package kotetsu.auth.application.dto.input;

import lombok.Getter;

public class CheckRedirectUriInput {
    @Getter
    private final String clientId;

    @Getter
    private final String redirectUri;

    private CheckRedirectUriInput(final String clientId, final String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public CheckRedirectUriInput of(final String clientId, final String redirectUri) {
        final CheckRedirectUriInput input = new CheckRedirectUriInput(clientId, redirectUri);

        return input;
    }
}
