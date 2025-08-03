package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ClientId {
    @Getter
    @NotBlank
    private final String value;

    private ClientId(final String value) {
        this.value = value;
    }

    public static ClientId of(final String value) {
        final ClientId clientId = new ClientId(value);
        
        return clientId;
    }
}
