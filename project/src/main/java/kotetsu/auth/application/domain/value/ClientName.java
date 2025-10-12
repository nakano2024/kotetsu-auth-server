package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ClientName {
    @Getter
    @NotBlank
    private final String value;

    private ClientName(final String value) {
        this.value = value;
    }

    public static ClientName of(final String value) {
        final ClientName clientName = new ClientName(value);
        return clientName;
    }
}
