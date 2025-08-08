package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchAccessTokenCorePort {
    PendingAccessTokenCore fetch(Key key);
}
