package kotetsu.auth.unit.domain.value.authorizationcodeverifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AuthorizationCodeVerifier;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AuthorizationCodeVerifier authorizationCodeVerifier = AuthorizationCodeVerifier.of("test-verifier");

        assertEquals("test-verifier", authorizationCodeVerifier.getValue());
    }
}