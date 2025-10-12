package kotetsu.auth.unit.domain.value.authorizationcode;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ExpiredAt;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AuthorizationCodeValue value = AuthorizationCodeValue.of("test-code");
        AuthorizationCodeChallenge challenge = AuthorizationCodeChallenge.of("test-challenge");
        ExpiredAt expiredAt = ExpiredAt.of(new Date(2000));
        
        AuthorizationCode authorizationCode = AuthorizationCode.of(value, challenge, expiredAt);

        assertEquals("test-code", authorizationCode.getValue().getValue());
        assertEquals("test-challenge", authorizationCode.getChallenge().getValue());
        assertEquals(new Date(2000), authorizationCode.getExpiredAt().getValue());
    }
}