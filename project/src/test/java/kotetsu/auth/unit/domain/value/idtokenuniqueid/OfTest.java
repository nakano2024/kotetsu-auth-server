package kotetsu.auth.unit.domain.value.idtokenuniqueid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.IdTokenUniqueId;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        IdTokenUniqueId idTokenUniqueId = IdTokenUniqueId.of("test-unique-id");

        assertEquals("test-unique-id", idTokenUniqueId.getValue());
    }
}