package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ClientBasicInformation;
import kotetsu.auth.application.domain.value.ClientId;

public interface IFetchClientBasicInformationPort {
    ClientBasicInformation fetch(ClientId clientId);
}
