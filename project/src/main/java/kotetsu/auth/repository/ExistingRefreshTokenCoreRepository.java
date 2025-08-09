package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenCorePort;
import kotetsu.auth.application.domain.value.Key;

public class ExistingRefreshTokenCoreRepository implements IFetchExistingRefreshTokenCorePort {
    
    @Override
    public Optional<ExistingRefreshTokenCore> fetch(Key key) {
        return Optional.empty();
    }
}