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
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea");
        LinkedIdTokenCoreKey linkedIdTokenCoreKey = LinkedIdTokenCoreKey.of("test-id-key");

        ExistingRefreshTokenCore existingRefreshTokenCore = ExistingRefreshTokenCore.of(
            key,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey
        );

        assertEquals("test-key", existingRefreshTokenCore.getKey().getValue());
        assertEquals("3498665a-6863-7065-62ee-0be766cff4ea", existingRefreshTokenCore.getLinkedAccessTokenCoreKey().getValue());
        assertEquals("test-id-key", existingRefreshTokenCore.getLinkedIdTokenCoreKey().getValue());
    }
}