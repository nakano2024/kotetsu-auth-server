package kotetsu.auth.unit.domain.value.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Subject;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Subject subject = Subject.of("test-subject");

        assertEquals("test-subject", subject.getValue());
    }
}