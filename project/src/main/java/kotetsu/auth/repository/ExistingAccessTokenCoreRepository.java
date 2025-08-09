package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.value.Key;

public class ExistingAccessTokenCoreRepository implements IFetchExistingAccessTokenCorePort {
    
    @Override
    public Optional<ExistingAccessTokenCore> fetch(Key key) {
        return Optional.empty();
    }
}