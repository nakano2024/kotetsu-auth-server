package kotetsu.auth.unit.domain.value.accesstokenvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AccessTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        AccessTokenValue accessTokenValue = AccessTokenValue.of("test-access-token");

        assertEquals("test-access-token", accessTokenValue.getValue());
    }
}