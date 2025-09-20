package kotetsu.auth.unit.domain.entity.requestedscopelist;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;

public class ToScopeListTokenTest {
    @Test
    // 複数パターンで検証したい
    public void returnResultMatchingAllArgumentScopes() {
        List<Scope> scopes = List.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("task.read")),
            Scope.of(Key.of("test-key2"), ScopeName.of("task.write")),
            Scope.of(Key.of("test-key3"), ScopeName.of("task.delete"))
        );

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);

        assertEquals("task.read task.write task.delete", requestedScopeList.toScopeListToken());
    }

    @Test
    public void returnEmptyStringIfArgumentScopesIsEmpty() {
        List<Scope> scopes = List.of();

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);

        assertEquals("", requestedScopeList.toScopeListToken());
    }
}
