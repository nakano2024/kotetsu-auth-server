package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;

public interface IStorePendingAccessTokenCorePort {
    void store(PendingAccessTokenCore accessTokenBody);
}
