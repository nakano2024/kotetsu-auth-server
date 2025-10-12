package kotetsu.auth.unit.domain.value.issuedat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.IssuedAt;

public class GetUnixSecTest {
    @Test
    public void returnCorrectResult() {
        IssuedAt expiredAt = IssuedAt.of(Date.from(
            LocalDateTime.of(2025, 9, 12, 0, 0, 0).atZone(ZoneId.of("UTC")).toInstant()
        ));

        assertEquals(1757635200, expiredAt.getUnixSec());
    }
}
