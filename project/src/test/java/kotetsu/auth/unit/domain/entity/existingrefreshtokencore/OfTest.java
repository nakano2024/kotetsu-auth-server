package kotetsu.auth.unit.domain.entity.existingrefreshtokencore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("test-access-key");
        LinkedIdTokenCoreKey linkedIdTokenCoreKey = LinkedIdTokenCoreKey.of("test-id-key");

        ExistingRefreshTokenCore existingRefreshTokenCore = ExistingRefreshTokenCore.of(
            key,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey
        );

        assertEquals("test-key", existingRefreshTokenCore.getKey().getValue());
        assertEquals("test-access-key", existingRefreshTokenCore.getLinkedAccessTokenCoreKey().getValue());
        assertEquals("test-id-key", existingRefreshTokenCore.getLinkedIdTokenCoreKey().getValue());
    }
}