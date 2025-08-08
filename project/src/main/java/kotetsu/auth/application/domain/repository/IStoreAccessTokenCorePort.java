package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;

public interface IStoreAccessTokenCorePort {
    void store(PendingAccessTokenCore accessTokenBody);
}
