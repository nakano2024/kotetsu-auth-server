package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import lombok.Getter;

public class IssuedAccessToken {
    public static final String TOKEN_TYPE = "Bearer";
    public static final int EXPIRES_HOURS = 1;

    @Getter
    @NotNull
    final AccessTokenValue value;

    @Getter
    @NotNull
    final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey;

    @Getter
    @NotNull
    final Duration duration;

    private IssuedAccessToken(
        final AccessTokenValue value,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        this.value = value;
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.duration = duration;
    }

    public static IssuedAccessToken of(
        final AccessTokenValue value,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        final IssuedAccessToken pendingAccessToken = new IssuedAccessToken(value, linkedAccessTokenCoreKey, duration);
        return pendingAccessToken;
    }
}
