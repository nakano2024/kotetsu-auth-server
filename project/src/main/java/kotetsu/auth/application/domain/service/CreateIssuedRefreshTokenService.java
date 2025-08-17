package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.domain.util.IGenerateRefreshTokenValuePort;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

public class CreateIssuedRefreshTokenService {
    private final IGenerateRefreshTokenValuePort generateRefreshTokenValuePort;

    public CreateIssuedRefreshTokenService(
        final IGenerateRefreshTokenValuePort generateRefreshTokenValuePort
    ) {
        this.generateRefreshTokenValuePort = generateRefreshTokenValuePort;
    }

    public IssuedRefreshToken create(final LinkedRefreshTokenCoreKey coreKey, final IssuedAt issuedAt) {
        return IssuedRefreshToken.of(
            generateRefreshTokenValuePort.generate(RefreshTokenValue.LENGTH),
            coreKey,
            Duration.of(
                issuedAt,
                ExpiredAt.of(Date.from(issuedAt.getValue().toInstant().plus(IssuedIdTokenMeta.EXPIRES_HOURS, ChronoUnit.HOURS)))
            )
        );
    }
}
