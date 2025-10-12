package kotetsu.auth.unit.domain.entity.issuedrefreshtoken;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RefreshTokenValue value = RefreshTokenValue.of("test-refresh-token-value");
        LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey = LinkedRefreshTokenCoreKey.of("test-linked-key");
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);

        IssuedRefreshToken issuedRefreshToken = IssuedRefreshToken.of(
            value,
            linkedRefreshTokenCoreKey,
            duration
        );

        assertEquals("test-refresh-token-value", issuedRefreshToken.getValue().getValue());
        assertEquals("test-linked-key", issuedRefreshToken.getLinkedRefreshTokenCoreKey().getValue());
        assertEquals(new Date(1000), issuedRefreshToken.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), issuedRefreshToken.getDuration().getExpiredAt().getValue());
    }
}