package kotetsu.auth.unit.domain.value.nonce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Nonce;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Nonce nonce = Nonce.of("test-nonce-value");

        assertEquals("test-nonce-value", nonce.getValue());
    }
}