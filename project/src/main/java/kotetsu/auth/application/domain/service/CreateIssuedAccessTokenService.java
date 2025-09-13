package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.util.IGenerateAccessTokenValuePort;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public class CreateIssuedAccessTokenService {
    private final IGenerateAccessTokenValuePort generateAccessTokenValuePort;

    public CreateIssuedAccessTokenService(final IGenerateAccessTokenValuePort generateAccessTokenValuePort) {
        this.generateAccessTokenValuePort = generateAccessTokenValuePort;
    }

    public IssuedAccessToken create(final LinkedAccessTokenCoreKey coreKey, final IssuedAt issuedAt) {
        return IssuedAccessToken.of(
            generateAccessTokenValuePort.generate(AccessTokenValue.LENGTH),
            coreKey,
            Duration.of(
                issuedAt,
                ExpiredAt.of(Date.from(issuedAt.getValue().toInstant().plus(IssuedAccessToken.EXPIRES_HOURS, ChronoUnit.HOURS)))
            )
        );
    }
}
