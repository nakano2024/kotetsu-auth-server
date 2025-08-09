package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchExistingAccessTokenCorePort {
    Optional<ExistingAccessTokenCore> fetch(Key key);
}
