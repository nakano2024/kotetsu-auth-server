package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.AccessTokenBody;

public interface IStoreAccessTokenBodyPort {
    void store(AccessTokenBody accessTokenBody);
}
