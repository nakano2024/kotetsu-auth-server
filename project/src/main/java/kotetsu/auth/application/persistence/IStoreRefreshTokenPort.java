package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.store.RefreshTokenStore;

public interface IStoreRefreshTokenPort {
    String store(final RefreshTokenStore refreshToken);
}