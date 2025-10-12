package kotetsu.auth.unit.domain.value.clientredirecturi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ClientRedirectUri;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        ClientRedirectUri clientRedirectUri = ClientRedirectUri.of("https://example.com/callback");

        assertEquals("https://example.com/callback", clientRedirectUri.getValue());
    }
}