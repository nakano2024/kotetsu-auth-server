package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import lombok.Getter;

public class ExistingRefreshTokenCore {
    @Getter
    @NotNull
    private final Key key;

    @Getter
    @NotNull
    private final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey;

    @Getter
    @NotNull
    private final LinkedIdTokenCoreKey linkedIdTokenCoreKey;

    private ExistingRefreshTokenCore(
        final Key key,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final LinkedIdTokenCoreKey linkedIdTokenCoreKey
    ) {
        this.key = key;
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.linkedIdTokenCoreKey = linkedIdTokenCoreKey;
    }

    public static ExistingRefreshTokenCore of(
        final Key key,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final LinkedIdTokenCoreKey linkedIdTokenCoreKey
    ) {
        ExistingRefreshTokenCore refreshTokenCore = new ExistingRefreshTokenCore(
            key,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey
        );

        return refreshTokenCore;
    }
}
