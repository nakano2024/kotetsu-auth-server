package kotetsu.auth.unit.domain.entity.permittedscopelist;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;

public class ContainsAllTest {
    @Test
    public void returnTrueIfArgumentsScopesAllContained() {
        Set<Scope> scopes = Set.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of("test-key2"), ScopeName.of("write")),
            Scope.of(Key.of("test-key3"), ScopeName.of("delete"))
        );

        PermittedScopeList permittedScopeList = PermittedScopeList.of(scopes);
        assertTrue(permittedScopeList.containsAll(Set.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of("test-key2"), ScopeName.of("write"))
        )));
    }

    @Test
    public void returnsFalseIfEvenASingleArgumentScopeIsMissing() {
        Set<Scope> scopes = Set.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of("test-key2"), ScopeName.of("write")),
            Scope.of(Key.of("test-key3"), ScopeName.of("delete"))
        );

        PermittedScopeList permittedScopeList = PermittedScopeList.of(scopes);
        assertFalse(permittedScopeList.containsAll(Set.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of("not-containd"), ScopeName.of("not.containd"))
        )));
    }
}