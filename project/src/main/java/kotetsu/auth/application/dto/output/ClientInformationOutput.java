package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class ClientInformationOutput {
    @Getter
    private final String clientId;

    @Getter
    private final String name;

    private ClientInformationOutput(final String clientId, final String name) {
        this.clientId = clientId;
        this.name = name;
    }

    public static ClientInformationOutput of(final String clientId, final String name) {
        final ClientInformationOutput output = new ClientInformationOutput(clientId, name);

        return output;
    }
}
