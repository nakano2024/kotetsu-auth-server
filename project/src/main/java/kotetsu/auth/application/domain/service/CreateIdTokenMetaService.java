package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.domain.entity.IdTokenMeta;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;

public class CreateIdTokenMetaService {
    public IdTokenMeta create(final IdTokenUniqueId uniqueId, final IssuedAt issuedAt) {
        return IdTokenMeta.of(
            Duration.of(
                issuedAt,
                ExpiredAt.of(Date.from(issuedAt.getValue().toInstant().plus(IdTokenMeta.EXPIRES_HOURS, ChronoUnit.HOURS)))
            ),
            uniqueId
        );
    }
}
