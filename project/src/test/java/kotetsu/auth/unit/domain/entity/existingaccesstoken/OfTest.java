package kotetsu.auth.unit.domain.entity.existingaccesstoken;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("9226f75a-7082-b7aa-f7b9-317f0fb98274");
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("9226f75a-7082-b7aa-f7b9-317f0fb98275");
        IssuedAt issuedAt = IssuedAt.of(new Date(1000));
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        Duration duration = Duration.of(issuedAt, expiredAt);

        ExistingAccessToken existingAccessToken = ExistingAccessToken.of(
            key,
            linkedAccessTokenCoreKey,
            duration
        );

        assertEquals("9226f75a-7082-b7aa-f7b9-317f0fb98274", existingAccessToken.getKey().getValue());
        assertEquals("9226f75a-7082-b7aa-f7b9-317f0fb98275", existingAccessToken.getLinkedAccessTokenCoreKey().getValue());
        assertEquals(new Date(1000), existingAccessToken.getDuration().getIssuedAt().getValue());
        assertEquals(new Date(2000), existingAccessToken.getDuration().getExpiredAt().getValue());
    }
}
