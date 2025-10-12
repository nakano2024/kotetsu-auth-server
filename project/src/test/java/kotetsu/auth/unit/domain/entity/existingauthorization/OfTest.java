package kotetsu.auth.unit.domain.entity.existingauthorization;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        AuthorizationCode authorizationCode = AuthorizationCode.of(
            AuthorizationCodeValue.of("test-code"),
            AuthorizationCodeChallenge.of("test-challenge"),
            ExpiredAt.of(new Date(2000))
        );
        AccessType accessType = AccessType.of("online");
        LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = LinkedAccessTokenCoreKey.of("3498665a-6863-7065-62ee-0be766cff4ea");
        LinkedIdTokenCoreKey linkedIdTokenCoreKey = LinkedIdTokenCoreKey.of("test-id-key");
        LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey = LinkedRefreshTokenCoreKey.of("test-refresh-key");
        GrantType grantType = GrantType.of("authorization_code");

        ExistingAuthorization existingAuthorization = ExistingAuthorization.of(
            key,
            authorizationCode,
            accessType,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey,
            linkedRefreshTokenCoreKey,
            grantType
        );

        assertEquals("test-key", existingAuthorization.getKey().getValue());
        assertEquals("test-code", existingAuthorization.getAuthorizationCode().getValue().getValue());
        assertEquals("online", existingAuthorization.getAccessType().getValue());
        assertEquals("3498665a-6863-7065-62ee-0be766cff4ea", existingAuthorization.getLinkedAccessTokenCoreKey().getValue());
        assertEquals("test-id-key", existingAuthorization.getLinkedIdTokenCoreKey().getValue());
        assertEquals("test-refresh-key", existingAuthorization.getLinkedRefreshTokenCoreKey().getValue());
        assertEquals("authorization_code", existingAuthorization.getGrantType().getValue());
    }
}