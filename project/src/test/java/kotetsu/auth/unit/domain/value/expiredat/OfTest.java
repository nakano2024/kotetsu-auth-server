package kotetsu.auth.unit.domain.value.expiredat;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ExpiredAt;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Date testDate = new Date(2000);
        ExpiredAt expiredAt = ExpiredAt.of(testDate);

        assertEquals(testDate, expiredAt.getValue());
    }
}