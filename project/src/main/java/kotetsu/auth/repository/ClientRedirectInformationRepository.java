package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ClientRedirectInformation;
import kotetsu.auth.application.domain.repository.IFetchClientRedirectInformationPort;
import kotetsu.auth.application.domain.value.ClientId;

public class ClientRedirectInformationRepository implements IFetchClientRedirectInformationPort {
    
    @Override
    public Optional<ClientRedirectInformation> fetch(ClientId clientId) {
        return Optional.empty();
    }
}