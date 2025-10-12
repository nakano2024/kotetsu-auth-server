package kotetsu.auth.unit.domain.entity.issuedidtokenmeta;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        LinkedIdTokenCoreKey linkedIdTokenCoreKey = LinkedIdTokenCoreKey.of("test-linked-key");
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);
        IdTokenUniqueId uniqueId = IdTokenUniqueId.of("test-unique-id");

        IssuedIdTokenMeta issuedIdTokenMeta = IssuedIdTokenMeta.of(
            linkedIdTokenCoreKey,
            duration,
            uniqueId
        );

        assertEquals("test-linked-key", issuedIdTokenMeta.getLinkedIdTokenCoreKey().getValue());
        assertEquals(new Date(1000), issuedIdTokenMeta.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), issuedIdTokenMeta.getDuration().getExpiredAt().getValue());
        assertEquals("test-unique-id", issuedIdTokenMeta.getUniqueId().getValue());
    }
}