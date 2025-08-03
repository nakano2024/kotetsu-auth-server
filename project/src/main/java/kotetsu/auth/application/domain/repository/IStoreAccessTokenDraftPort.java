package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.AccessTokenDraft;

public interface IStoreAccessTokenDraftPort {
    void store(AccessTokenDraft accessTokenBody);
}
