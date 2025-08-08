package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import lombok.Getter;

public class PendingRefreshTokenCore {
    @Getter
    @NotNull
    private final Key key;

    @Getter
    @NotNull
    private final LinkedAccessTokenCoreKey linkedAccessTokenCoreId;

    @Getter
    @NotNull
    private final LinkedIdTokenCoreKey linkedIdTokenCoreId;

    private PendingRefreshTokenCore(
        final Key key,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreId,
        final LinkedIdTokenCoreKey linkedIdTokenCoreId
    ) {
        this.key = key;
        this.linkedAccessTokenCoreId = linkedAccessTokenCoreId;
        this.linkedIdTokenCoreId = linkedIdTokenCoreId;
    }

    public static PendingRefreshTokenCore of(
        final Key key,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreId,
        final LinkedIdTokenCoreKey linkedIdTokenCoreId
    ) {
        PendingRefreshTokenCore refreshTokenCore = new PendingRefreshTokenCore(
            key,
            linkedAccessTokenCoreId,
            linkedIdTokenCoreId
        );

        return refreshTokenCore;
    }
}
