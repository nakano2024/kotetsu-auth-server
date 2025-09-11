package kotetsu.auth.unit.domain.value.accesstokenaudience;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AccessTokenAudience;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AccessTokenAudience accessTokenAudience = AccessTokenAudience.of("https://api.example.com");

        assertEquals("https://api.example.com", accessTokenAudience.getValue());
    }
}