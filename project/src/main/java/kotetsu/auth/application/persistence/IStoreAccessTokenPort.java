package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.store.AccessTokenStore;

public interface IStoreAccessTokenPort {
    String store(final AccessTokenStore accessToken);
}