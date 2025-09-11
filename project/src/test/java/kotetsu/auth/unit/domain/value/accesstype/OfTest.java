package kotetsu.auth.unit.domain.value.accesstype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AccessType;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AccessType accessType = AccessType.of("online");

        assertEquals("online", accessType.getValue());
    }
}