package kotetsu.auth.application.dto.data;

import lombok.Getter;

public class ClientCredentialData {
    @Getter
    private String clientId;

    @Getter
    private String clientSecret;

    private ClientCredentialData(final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    
    public ClientCredentialData of(final String clientId, final String clientSecret) {
        return new ClientCredentialData(clientId, clientSecret);
    }
}
