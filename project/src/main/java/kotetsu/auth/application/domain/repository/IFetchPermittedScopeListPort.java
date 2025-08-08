package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchPermittedScopeListPort {
    PermittedScopeList fetch(Key clientKey);
}
