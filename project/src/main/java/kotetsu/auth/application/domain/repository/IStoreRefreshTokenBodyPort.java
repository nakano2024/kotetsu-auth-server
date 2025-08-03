package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.RefreshTokenBody;

public interface IStoreRefreshTokenBodyPort {
    void store(RefreshTokenBody refreshTokenBody);
}
