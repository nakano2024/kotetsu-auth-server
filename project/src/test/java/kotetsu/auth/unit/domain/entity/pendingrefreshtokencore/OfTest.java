package kotetsu.auth.unit.domain.entity.pendingrefreshtokencore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        LinkedAccessTokenCoreKey linkedAccessTokenCoreId = LinkedAccessTokenCoreKey.of("test-access-key");
        LinkedIdTokenCoreKey linkedIdTokenCoreId = LinkedIdTokenCoreKey.of("test-id-key");

        PendingRefreshTokenCore pendingRefreshTokenCore = PendingRefreshTokenCore.of(
            key,
            linkedAccessTokenCoreId,
            linkedIdTokenCoreId
        );

        assertEquals("test-key", pendingRefreshTokenCore.getKey().getValue());
        assertEquals("test-access-key", pendingRefreshTokenCore.getLinkedAccessTokenCoreId().getValue());
        assertEquals("test-id-key", pendingRefreshTokenCore.getLinkedIdTokenCoreId().getValue());
    }
}