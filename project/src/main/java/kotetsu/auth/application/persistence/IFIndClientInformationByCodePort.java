package kotetsu.auth.application.persistence;

import java.util.UUID;

import kotetsu.auth.application.dto.data.ClientInformationData;

public interface IFIndClientInformationByCodePort {
    ClientInformationData findByCode(UUID code);
}
