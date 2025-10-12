package kotetsu.auth.unit.domain.value.linkedidtokencorekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        LinkedIdTokenCoreKey linkedIdTokenCoreKey = LinkedIdTokenCoreKey.of("test-linked-id-key");

        assertEquals("test-linked-id-key", linkedIdTokenCoreKey.getValue());
    }
}