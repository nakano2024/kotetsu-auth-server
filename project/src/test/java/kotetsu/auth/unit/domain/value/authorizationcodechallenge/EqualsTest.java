package kotetsu.auth.unit.domain.value.authorizationcodechallenge;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;

public class EqualsTest {
    @Test
    public void returnTrueIfArgumentValueEquals() {
        AuthorizationCodeChallenge authorizationCodeChallenge = AuthorizationCodeChallenge.of("test-challenge");
        AuthorizationCodeChallenge anotherAuthorizationCodeChallenge = AuthorizationCodeChallenge.of("test-challenge");

        assertTrue(authorizationCodeChallenge.equals(anotherAuthorizationCodeChallenge));
    }

        @Test
    public void returnFalseIfArgumentValueNotEquals() {
        AuthorizationCodeChallenge authorizationCodeChallenge = AuthorizationCodeChallenge.of("test-challenge");
        AuthorizationCodeChallenge anotherAuthorizationCodeChallenge = AuthorizationCodeChallenge.of("another-test-challenge");

        assertFalse(authorizationCodeChallenge.equals(anotherAuthorizationCodeChallenge));
    }
}
