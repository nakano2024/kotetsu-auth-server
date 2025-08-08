package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;

public interface IStoreRefreshTokenCorePort {
    void store(PendingRefreshTokenCore refreshTokenBody);
}
