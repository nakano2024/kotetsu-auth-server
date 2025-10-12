package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.IssuedAccessToken;

public interface IStoreIssuedAccessTokenPort {
    void store(IssuedAccessToken issuedAccessToken);
}
