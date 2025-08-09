package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ClientRedirectInformation;
import kotetsu.auth.application.domain.value.ClientId;

public interface IFetchClientRedirectInformationPort {
    Optional<ClientRedirectInformation> fetch(ClientId clientId);
}
