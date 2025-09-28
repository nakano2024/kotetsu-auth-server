package kotetsu.auth.unit.domain.value.linkedaccesstokencorekey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.exception.LinkedAccessTokenCoreKeyValidationRuntimeException;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea");

        assertEquals("3498665a-6863-7065-62ee-0be766cff4ea", linkedAccessTokenCoreKey.getValue());
    }

    @Test
    public void throwIfNull() {
        assertThrows(LinkedAccessTokenCoreKeyValidationRuntimeException.class, () -> {
            LinkedAccessTokenCoreKey.of(null);
        });
    }
}