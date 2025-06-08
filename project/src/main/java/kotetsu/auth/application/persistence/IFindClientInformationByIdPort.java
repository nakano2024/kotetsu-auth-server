package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.data.ClientInformationData;

public interface IFindClientInformationByIdPort {
    ClientInformationData findById(String id);
}
