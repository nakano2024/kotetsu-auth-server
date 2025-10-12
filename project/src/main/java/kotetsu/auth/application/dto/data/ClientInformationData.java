package kotetsu.auth.application.dto.data;

import lombok.Getter;

public class ClientInformationData {
    @Getter
    private final String clientId;

    @Getter
    private final String name;

    private ClientInformationData(final String clientId, final String name) {
        this.clientId = clientId;
        this.name = name;
    }

    public static ClientInformationData of(final String clientId, final String name) {
        final ClientInformationData clientInformationData = new ClientInformationData(clientId, name);

        return clientInformationData;
    }
}
