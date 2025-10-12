package kotetsu.auth.unit.domain.value.internalauthtokenvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.InternalAuthTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        InternalAuthTokenValue internalAuthTokenValue = InternalAuthTokenValue.of("test-internal-token");

        assertEquals("test-internal-token", internalAuthTokenValue.getValue());
    }
}