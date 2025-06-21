package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.data.ClientInformationData;

public interface IFindClientInformationByIdAndSecretPort {
    ClientInformationData findByIdAndSecret(final String clientId, final String clientSecret);
}