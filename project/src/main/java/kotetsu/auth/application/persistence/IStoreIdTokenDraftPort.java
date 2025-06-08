package kotetsu.auth.application.persistence;

import java.util.UUID;

import kotetsu.auth.application.dto.store.IdTokenDraftStore;

public interface IStoreIdTokenDraftPort {
    UUID store(IdTokenDraftStore idToken);
}
