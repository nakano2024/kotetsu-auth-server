package kotetsu.auth.unit.domain.value.useractivation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.UserActivation;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        UserActivation userActivation = UserActivation.of(true);

        assertTrue(userActivation.isActive());
    }
}