package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import lombok.Getter;

public class ExistingRefreshToken {
    @Getter
    @NotNull
    private final Key key;

    @Getter
    @NotNull
    private final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey;

    @Getter
    @NotNull
    private final Duration duration;

    @Getter
    @NotNull
    private final GrantType grantType;

    private ExistingRefreshToken(
        final Key key,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey,
        final Duration duration,
        final GrantType grantType
    ) {
        this.key = key;
        this.linkedRefreshTokenCoreKey = linkedRefreshTokenCoreKey;
        this.duration = duration;
        this.grantType = grantType;
    }

    public static ExistingRefreshToken of(
        final Key key,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey,
        final Duration duration,
        final GrantType grantType
    ) {
        final ExistingRefreshToken existingRefreshToken = new ExistingRefreshToken(
            key,
            linkedRefreshTokenCoreKey,
            duration,
            grantType
        );

        return existingRefreshToken;
    }
}
