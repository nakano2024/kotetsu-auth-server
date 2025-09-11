package kotetsu.auth.unit.domain.value.email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Email;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Email email = Email.of("test@example.com");

        assertEquals("test@example.com", email.getValue());
    }
}