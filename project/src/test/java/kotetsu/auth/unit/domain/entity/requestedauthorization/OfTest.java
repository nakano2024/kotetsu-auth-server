package kotetsu.auth.unit.domain.entity.requestedauthorization;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AuthorizationCode authorizationCode = AuthorizationCode.of(
            AuthorizationCodeValue.of("test-code"),
            AuthorizationCodeChallenge.of("test-challenge"),
            ExpiredAt.of(new Date(2000))
        );
        AccessType accessType = AccessType.of("online");
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea");
        LinkedIdTokenCoreKey linkedIdTokenCoreKey = LinkedIdTokenCoreKey.of("test-id-key");
        LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey = LinkedRefreshTokenCoreKey.of("test-refresh-key");

        RequestedAuthorization requestedAuthorization = RequestedAuthorization.of(
            authorizationCode,
            accessType,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey,
            linkedRefreshTokenCoreKey
        );

        assertEquals("test-code", requestedAuthorization.getAuthorizationCode().getValue().getValue());
        assertEquals("online", requestedAuthorization.getAccessType().getValue());
        assertEquals("3498665a-6863-7065-62ee-0be766cff4ea", requestedAuthorization.getLinkedAccessTokenCoreKey().getValue());
        assertEquals("test-id-key", requestedAuthorization.getLinkedIdTokenCoreKey().getValue());
        assertEquals("test-refresh-key", requestedAuthorization.getLinkedRefreshTokenCoreKey().getValue());
    }
}