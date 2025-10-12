package kotetsu.auth.application.query;

import java.util.Optional;

import kotetsu.auth.application.dto.data.ClientInformationData;

public interface IFindClientInformationPort {
    Optional<ClientInformationData> findByClientId(String clientId);
}
