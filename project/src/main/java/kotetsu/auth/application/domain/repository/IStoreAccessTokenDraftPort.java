package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.AccessTokenBody;

public interface IStoreAccessTokenDraftPort {
    void store(AccessTokenBody accessTokenBody);
}
