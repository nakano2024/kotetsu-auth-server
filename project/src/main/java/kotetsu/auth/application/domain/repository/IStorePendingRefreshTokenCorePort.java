package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;

public interface IStorePendingRefreshTokenCorePort {
    void store(PendingRefreshTokenCore refreshTokenBody);
}
