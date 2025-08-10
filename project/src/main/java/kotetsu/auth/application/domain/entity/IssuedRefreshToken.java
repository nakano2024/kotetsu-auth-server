package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.GrantType;
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

    @Getter
    @NotNull
    private final GrantType grantType;

    private IssuedRefreshToken(
        final RefreshTokenValue value,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey,
        final Duration duration,
        final GrantType grantType
    ) {
        this.value = value;
        this.linkedRefreshTokenCoreKey = linkedRefreshTokenCoreKey;
        this.duration = duration;
        this.grantType = grantType;
    }

    public static IssuedRefreshToken of(final RefreshTokenValue value, final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey, final Duration duration) {
        final IssuedRefreshToken issuedRefreshToken = new IssuedRefreshToken(
            value,
            linkedRefreshTokenCoreKey,
            duration,
            GrantType.of(GrantType.GRANT_TYPE_REFRESH_TOKEN)
        );

        return issuedRefreshToken;
    }
}
