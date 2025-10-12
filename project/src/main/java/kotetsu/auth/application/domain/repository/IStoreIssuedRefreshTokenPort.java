package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.IssuedRefreshToken;

public interface IStoreIssuedRefreshTokenPort {
    void store(IssuedRefreshToken issuedRefreshToken);
}
