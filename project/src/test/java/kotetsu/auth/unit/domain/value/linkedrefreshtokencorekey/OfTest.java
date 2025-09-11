package kotetsu.auth.unit.domain.value.linkedrefreshtokencorekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey = LinkedRefreshTokenCoreKey.of("test-linked-refresh-key");

        assertEquals("test-linked-refresh-key", linkedRefreshTokenCoreKey.getValue());
    }
}