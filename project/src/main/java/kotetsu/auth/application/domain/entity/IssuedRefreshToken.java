package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.RefreshTokenValue;
import lombok.Getter;

public class IssuedRefreshToken {
    public static final int EXPIRES_WEEKS = 1;

    @Getter
    @NotNull
    private final RefreshTokenValue value;

    @Getter
    @NotNull
    private final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey;

    @Getter
    @NotNull
    private final Duration duration;

    private IssuedRefreshToken(final RefreshTokenValue value, final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey, final Duration duration) {
        this.value = value;
        this.linkedRefreshTokenCoreKey = linkedRefreshTokenCoreKey;
        this.duration = duration;
    }

    public static IssuedRefreshToken of(final RefreshTokenValue value, final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey, final Duration duration) {
        final IssuedRefreshToken issuedRefreshToken = new IssuedRefreshToken(value, linkedRefreshTokenCoreKey, duration);

        return issuedRefreshToken;
    }
}
