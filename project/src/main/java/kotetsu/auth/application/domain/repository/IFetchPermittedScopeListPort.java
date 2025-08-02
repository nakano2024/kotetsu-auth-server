package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.value.Id;

public interface IFetchPermittedScopeListPort {
    PermittedScopeList fetch(Id clientCode);
}
