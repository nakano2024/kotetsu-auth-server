package kotetsu.auth.unit.domain.value.idtokenvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.IdTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        IdTokenValue idTokenValue = IdTokenValue.of("test-id-token");

        assertEquals("test-id-token", idTokenValue.getValue());
    }
}