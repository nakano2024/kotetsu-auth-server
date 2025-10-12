package kotetsu.auth.unit.domain.entity.requestedscopelist;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        List<Scope> scopes = List.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of("test-key2"), ScopeName.of("write"))
        );

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);

        Set<Scope> expectedScopeSet = Set.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of("test-key2"), ScopeName.of("write"))
        );

        assertEquals(expectedScopeSet, requestedScopeList.getScopes());
    }
}