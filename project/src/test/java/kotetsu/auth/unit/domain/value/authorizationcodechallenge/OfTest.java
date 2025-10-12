package kotetsu.auth.unit.domain.value.authorizationcodechallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AuthorizationCodeChallenge authorizationCodeChallenge = AuthorizationCodeChallenge.of("test-challenge");

        assertEquals("test-challenge", authorizationCodeChallenge.getValue());
    }
}