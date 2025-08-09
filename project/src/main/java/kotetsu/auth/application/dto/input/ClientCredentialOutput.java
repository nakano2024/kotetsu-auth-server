package kotetsu.auth.application.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ClientCredentialOutput {
    @Getter
    @NotBlank
    private final String clientId;

    @Getter
    @NotBlank
    private final String clientSecret;

    private ClientCredentialOutput(final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public static ClientCredentialOutput of(final String clientId, final String clientSecret) {
        final ClientCredentialOutput output = new ClientCredentialOutput(clientId, clientSecret);

        return output;
    }
}
