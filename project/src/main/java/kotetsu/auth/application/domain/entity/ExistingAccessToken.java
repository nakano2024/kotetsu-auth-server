package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import lombok.Getter;

public class ExistingAccessToken {
    @Getter
    @NotNull
    private final Key key;

    @Getter
    @NotNull
    private final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey;

    @Getter
    @NotNull
    private final Duration duration;

    private ExistingAccessToken(
        final Key key,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        this.key = key;
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.duration = duration;
    }

    public static ExistingAccessToken of(
        final Key key,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        final ExistingAccessToken accessToken = new ExistingAccessToken(key, linkedAccessTokenCoreKey, duration);
        return accessToken;
    }
}
