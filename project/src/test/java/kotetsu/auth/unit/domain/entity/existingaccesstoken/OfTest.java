package kotetsu.auth.unit.domain.entity.existingaccesstoken;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        final Date issuedAtDate = Date.from(
            LocalDateTime.of(2025, 9, 9, 0, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        final Date expiredAtDate = Date.from(
            LocalDateTime.of(2025, 9, 10, 0, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        );

        ExistingAccessToken existingAccessToken = ExistingAccessToken.of(
            Key.of("9226f75a-7082-b7aa-f7b9-317f0fb98274"),
            LinkedAccessTokenCoreKey.of("9226f75a-7082-b7aa-f7b9-317f0fb98274"),
            Duration.of(
                IssuedAt.of(issuedAtDate),
                ExpiredAt.of(expiredAtDate)
            )
        );
        assertEquals("9226f75a-7082-b7aa-f7b9-317f0fb98274", existingAccessToken.getKey().getValue());
        assertEquals("9226f75a-7082-b7aa-f7b9-317f0fb98274", existingAccessToken.getLinkedAccessTokenCoreKey().getValue());
        assertEquals(issuedAtDate, existingAccessToken.getDuration().getIssuedAt().getValue());
        assertEquals(expiredAtDate, existingAccessToken.getDuration().getExpiredAt().getValue());
    }
}
