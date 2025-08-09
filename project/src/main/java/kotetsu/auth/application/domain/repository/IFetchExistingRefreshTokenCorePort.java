package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface  IFetchExistingRefreshTokenCorePort {
    Optional<ExistingRefreshTokenCore> fetch(Key key);
}
