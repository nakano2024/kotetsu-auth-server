package kotetsu.auth.unit.domain.value.key;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.Key;

public class EqualsTest {
    @Test
    public void returnTrueIfEqualsToArgument() {
        Key key = Key.of("test-key-value");
        Key anotherKey = Key.of("test-key-value");

        assertTrue(key.equals(anotherKey));
    }

    @Test
    public void returnFalseIfNotEqualsToArgument() {
        Key key = Key.of("test-key-value");
        Key anotherKey = Key.of("another-test-key-value");

        assertFalse(key.equals(anotherKey));
    }
}
