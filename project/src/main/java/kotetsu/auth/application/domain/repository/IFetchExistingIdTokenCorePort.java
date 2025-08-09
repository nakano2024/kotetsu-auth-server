package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchExistingIdTokenCorePort {
    Optional<ExistingIdTokenCore> fetch(Key key);
}
