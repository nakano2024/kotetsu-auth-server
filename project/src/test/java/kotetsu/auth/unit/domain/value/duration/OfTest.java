package kotetsu.auth.unit.domain.value.duration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        
        Duration duration = Duration.of(issuedAt, expiredAt);

        assertEquals(new Date(1000), duration.getIssuedAt().getValue());
        assertEquals(new Date(2000), duration.getExpiredAt().getValue());
    }
}