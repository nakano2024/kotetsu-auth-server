package kotetsu.auth.unit.domain.entity.existingrefreshtoken;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingRefreshToken;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey = LinkedRefreshTokenCoreKey.of("test-linked-key");
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);
        GrantType grantType = GrantType.of("refresh_token");

        ExistingRefreshToken existingRefreshToken = ExistingRefreshToken.of(
            key,
            linkedRefreshTokenCoreKey,
            duration,
            grantType
        );

        assertEquals("test-key", existingRefreshToken.getKey().getValue());
        assertEquals("test-linked-key", existingRefreshToken.getLinkedRefreshTokenCoreKey().getValue());
        assertEquals(new Date(1000), existingRefreshToken.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), existingRefreshToken.getDuration().getExpiredAt().getValue());
        assertEquals("refresh_token", existingRefreshToken.getGrantType().getValue());
    }
}