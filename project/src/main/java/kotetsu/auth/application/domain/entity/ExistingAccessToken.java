package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import lombok.Getter;

public class ExistingAccessToken {
    @Getter
    @NotNull
    final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey;

    @Getter
    @NotNull
    final Duration duration;

    private ExistingAccessToken(
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.duration = duration;
    }

    public static ExistingAccessToken of(
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        final ExistingAccessToken accessToken = new ExistingAccessToken(linkedAccessTokenCoreKey, duration);
        return accessToken;
    }
}
