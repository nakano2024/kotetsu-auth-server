package kotetsu.auth.unit.domain.entity.existingidtokenmeta;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingIdTokenMeta;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);
        IdTokenUniqueId uniqueId = IdTokenUniqueId.of("test-unique-id");

        ExistingIdTokenMeta existingIdTokenMeta = ExistingIdTokenMeta.of(
            key,
            duration,
            uniqueId
        );

        assertEquals("test-key", existingIdTokenMeta.getKey().getValue());
        assertEquals(new Date(1000), existingIdTokenMeta.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), existingIdTokenMeta.getDuration().getExpiredAt().getValue());
        assertEquals("test-unique-id", existingIdTokenMeta.getUniqueId().getValue());
    }
}