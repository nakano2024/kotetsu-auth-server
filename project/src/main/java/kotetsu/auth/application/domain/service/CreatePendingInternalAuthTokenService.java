package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Subject;

public class CreatePendingInternalAuthTokenService {
    public PendingInternalAuthToken create(final Subject subject, final MeProfile profile, final IssuedAt issuedAt) {
        return PendingInternalAuthToken.of(
            subject,
            profile,
            Duration.of(
                issuedAt,
                ExpiredAt.of(Date.from(issuedAt.getValue().toInstant().plus(PendingInternalAuthToken.EXPIRES_DAYS, ChronoUnit.DAYS)))
            )
        );
    }
}
