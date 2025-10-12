package kotetsu.auth.application.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class GetClientCredentialInput {
    @Getter
    @NotBlank
    private final String clientId;

    private GetClientCredentialInput(final String clientId) {
        this.clientId = clientId;
    }

    public static GetClientCredentialInput of(final String clientId) {
        final GetClientCredentialInput input = new GetClientCredentialInput(clientId);

        return input;
    }
}
