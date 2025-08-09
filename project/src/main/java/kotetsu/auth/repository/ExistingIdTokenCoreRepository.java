package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenCorePort;
import kotetsu.auth.application.domain.value.Key;

public class ExistingIdTokenCoreRepository implements IFetchExistingIdTokenCorePort {
    
    @Override
    public Optional<ExistingIdTokenCore> fetch(Key key) {
        return Optional.empty();
    }
}