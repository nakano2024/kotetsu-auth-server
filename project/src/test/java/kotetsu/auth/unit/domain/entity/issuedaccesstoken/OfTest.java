package kotetsu.auth.unit.domain.entity.issuedaccesstoken;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AccessTokenValue value = AccessTokenValue.of("test-token-value");
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("test-linked-key");
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);

        IssuedAccessToken issuedAccessToken = IssuedAccessToken.of(
            value,
            linkedAccessTokenCoreKey,
            duration
        );

        assertEquals("test-token-value", issuedAccessToken.getValue().getValue());
        assertEquals("test-linked-key", issuedAccessToken.getLinkedAccessTokenCoreKey().getValue());
        assertEquals(new Date(1000), issuedAccessToken.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), issuedAccessToken.getDuration().getExpiredAt().getValue());
    }
}