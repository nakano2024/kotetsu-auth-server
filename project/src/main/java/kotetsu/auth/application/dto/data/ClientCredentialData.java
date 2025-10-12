package kotetsu.auth.application.dto.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ClientCredentialData {
    @Getter
    @NotBlank
    private final String clientId;

    @Getter
    @NotBlank
    private final String hashedClientSecret;

    private ClientCredentialData(final String clientId, final String hashedClientSecret) {
        this.clientId = clientId;
        this.hashedClientSecret = hashedClientSecret;
    }

    public static ClientCredentialData of(final String clientId, final String hashedClientSecret) {
        final ClientCredentialData clientCredential = new ClientCredentialData(clientId, hashedClientSecret);

        return clientCredential;
    }
}
