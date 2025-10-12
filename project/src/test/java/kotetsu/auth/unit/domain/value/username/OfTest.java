package kotetsu.auth.unit.domain.value.username;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.UserName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        UserName userName = UserName.of("test-user");

        assertEquals("test-user", userName.getValue());
    }
}