package kotetsu.auth.unit.domain.value.clientredirecturi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ClientRedirectUri;

public class EqualsTest {
    @Test
    public void returnTrueIfArgumentValueEquals() {
        ClientRedirectUri clientRedirectUri = ClientRedirectUri.of("https://example.com/callback");
        ClientRedirectUri anotherClientRedirectUri = ClientRedirectUri.of("https://example.com/callback");

        assertTrue(clientRedirectUri.equals(anotherClientRedirectUri));
    }

    @Test
    public void returnFalseIfArgumentValueEquals() {
        ClientRedirectUri clientRedirectUri = ClientRedirectUri.of("https://example.com/callback");
        ClientRedirectUri anotherClientRedirectUri = ClientRedirectUri.of("https://another.example.com/callback");

        assertFalse(clientRedirectUri.equals(anotherClientRedirectUri));
    }
}
