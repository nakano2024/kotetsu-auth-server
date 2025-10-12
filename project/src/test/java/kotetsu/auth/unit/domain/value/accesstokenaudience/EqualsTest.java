package kotetsu.auth.unit.domain.value.accesstokenaudience;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.AccessTokenAudience;

public class EqualsTest {
    @Test
    public void returnTrueIfArgumentIsSameValue() {
        AccessTokenAudience accessTokenAudience = AccessTokenAudience.of("https://api.example.com");
        AccessTokenAudience anotherAccessTokenAudience = AccessTokenAudience.of("https://api.example.com");
        assertTrue(accessTokenAudience.equals(anotherAccessTokenAudience));
    }

    @Test
    public void returnTrueIfArgumentIsDifferentValue() {
        AccessTokenAudience accessTokenAudience = AccessTokenAudience.of("https://api.example.com");
        AccessTokenAudience anotherAccessTokenAudience = AccessTokenAudience.of("https://another.api.example.com");
        assertFalse(accessTokenAudience.equals(anotherAccessTokenAudience));
    }
}
