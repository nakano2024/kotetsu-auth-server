package kotetsu.auth.application.query;

import java.util.Optional;

import kotetsu.auth.application.dto.data.ClientCredentialData;

public interface IFindClientCredentialPort {
    Optional<ClientCredentialData> findByClientId(String clientId);
}
