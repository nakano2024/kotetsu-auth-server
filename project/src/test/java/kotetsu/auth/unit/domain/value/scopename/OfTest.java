package kotetsu.auth.unit.domain.value.scopename;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ScopeName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        ScopeName scopeName = ScopeName.of("read");

        assertEquals("read", scopeName.getValue());
    }
}