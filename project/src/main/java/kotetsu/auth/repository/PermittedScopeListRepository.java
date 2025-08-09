package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.value.Key;

public class PermittedScopeListRepository implements IFetchPermittedScopeListPort {
    
    @Override
    public Optional<PermittedScopeList> fetch(Key clientKey) {
        return Optional.empty();
    }
}