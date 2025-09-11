package kotetsu.auth.unit.domain.value.linkedaccesstokencorekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("test-linked-access-key");

        assertEquals("test-linked-access-key", linkedAccessTokenCoreKey.getValue());
    }
}