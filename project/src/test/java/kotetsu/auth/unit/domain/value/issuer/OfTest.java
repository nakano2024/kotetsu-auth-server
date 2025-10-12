package kotetsu.auth.unit.domain.value.issuer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Issuer;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Issuer issuer = Issuer.of("https://example.com");

        assertEquals("https://example.com", issuer.getValue());
    }
}