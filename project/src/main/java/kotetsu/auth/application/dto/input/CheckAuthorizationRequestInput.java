package kotetsu.auth.application.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


public class CheckAuthorizationRequestInput {
    @Getter
    @NotBlank
    private final String clientId;

    @Getter
    @NotBlank
    private final String redirectUri;

    @Getter
    @NotBlank
    private final String scopeListToken;

    private CheckAuthorizationRequestInput(
        final String clientId,
        final String redirectUri,
        final String scopeListToken
    ) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scopeListToken = scopeListToken;
    }

    public static CheckAuthorizationRequestInput of(
        final String clientId,
        final String redirectUri,
        final String scopeListToken
    ) {
        final CheckAuthorizationRequestInput input = new CheckAuthorizationRequestInput(
            clientId,
            redirectUri,
            scopeListToken
        );

        return input;
    }
}
