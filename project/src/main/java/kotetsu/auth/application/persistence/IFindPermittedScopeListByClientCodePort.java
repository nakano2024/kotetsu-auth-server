package kotetsu.auth.application.persistence;

import java.util.List;
import java.util.UUID;

import kotetsu.auth.application.dto.data.ScopeData;

public interface IFindPermittedScopeListByClientCodePort {
    List<ScopeData> findByClientCode(UUID clientCode);
}
