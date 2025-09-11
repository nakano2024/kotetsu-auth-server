package kotetsu.auth.unit.domain.entity.scope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Scope scope = Scope.of(
            Key.of("test-key"),
            ScopeName.of("test-scope")
        );

        assertEquals("test-key", scope.getKey().getValue());
        assertEquals("test-scope", scope.getName().getValue());
    }
}