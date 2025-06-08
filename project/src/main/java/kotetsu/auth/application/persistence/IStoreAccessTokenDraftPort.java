package kotetsu.auth.application.persistence;

import java.util.UUID;

import kotetsu.auth.application.dto.store.AccessTokenDraftStore;

public interface IStoreAccessTokenDraftPort {
    UUID store(AccessTokenDraftStore accessTokenDraft);
}
