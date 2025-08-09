package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchAccessTokenCorePort {
    Optional<PendingAccessTokenCore> fetch(Key key);
}
