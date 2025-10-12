package kotetsu.auth.unit.domain.value.authorizationcodevalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AuthorizationCodeValue authorizationCodeValue = AuthorizationCodeValue.of("test-auth-code");

        assertEquals("test-auth-code", authorizationCodeValue.getValue());
    }
}