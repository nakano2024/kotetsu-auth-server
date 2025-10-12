package kotetsu.auth.unit.domain.value.issuedat;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.IssuedAt;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Date testDate = new Date(1000);
        IssuedAt issuedAt = IssuedAt.of(testDate);

        assertEquals(testDate, issuedAt.getValue());
    }
}