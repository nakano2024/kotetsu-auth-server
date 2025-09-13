package kotetsu.auth.unit.domain.service.createidtokenmetaservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.service.CreateIdTokenMetaService;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public class CreateTest {
    @Test
    public void createTest() {
        CreateIdTokenMetaService createIdTokenMetaService = new CreateIdTokenMetaService();

        IssuedIdTokenMeta meta = createIdTokenMetaService.create(
            LinkedIdTokenCoreKey.of("linked-id-token-core-key"),
            IdTokenUniqueId.of("unique-id"),
            IssuedAt.of(Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
            ))
        );

        assertEquals("linked-id-token-core-key", meta.getLinkedIdTokenCoreKey().getValue());
        assertEquals("unique-id", meta.getUniqueId().getValue());
        final Date expectedIssuedAt = Date.from(
                LocalDateTime.of(2025, 9, 13, 17, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedIssuedAt, meta.getDuration().getIssuedAt().getValue());

        final Date expectedExpiredAt = Date.from(
            LocalDateTime.of(2025, 9, 13, 18, 15, 1).atZone(ZoneId.of("UTC")).toInstant()
        );
        assertEquals(expectedExpiredAt, meta.getDuration().getExpiredAt().getValue());
    }
}