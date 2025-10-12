package kotetsu.auth.unit.domain.value.scopename;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.ScopeName;

public class EqualsTest {
    @Test
    public void returnTrueIfEqualsToArgument() {
        ScopeName scopeName = ScopeName.of("task.read");
        ScopeName anotherScopeName = ScopeName.of("task.read");

        assertTrue(scopeName.equals(anotherScopeName));
    }

    @Test
    public void returnFalseIfNotEqualsToArgument() {
        ScopeName scopeName = ScopeName.of("task.read");
        ScopeName anotherScopeName = ScopeName.of("task.delete");

        assertFalse(scopeName.equals(anotherScopeName));
    }
}
