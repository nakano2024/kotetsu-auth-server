package kotetsu.auth.unit.domain.value.refreshtokenvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.RefreshTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RefreshTokenValue refreshTokenValue = RefreshTokenValue.of("test-refresh-token");

        assertEquals("test-refresh-token", refreshTokenValue.getValue());
    }
}