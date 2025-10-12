package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchPermittedScopeListPort {
    Optional<PermittedScopeList> fetch(Key clientKey);
}
