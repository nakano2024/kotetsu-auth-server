package kotetsu.auth.unit.domain.value.idtokenaudience;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.IdTokenAudience;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        IdTokenAudience idTokenAudience = IdTokenAudience.of("test-audience");

        assertEquals("test-audience", idTokenAudience.getValue());
    }
}