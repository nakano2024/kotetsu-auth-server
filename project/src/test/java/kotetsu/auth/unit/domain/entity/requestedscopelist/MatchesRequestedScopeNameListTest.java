package kotetsu.auth.unit.domain.entity.requestedscopelist;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.ScopeName;

public class MatchesRequestedScopeNameListTest {
    @Test
    public void returnTrueIfMatchesRequestedScopeNameList() {
        List<Scope> scopes = List.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("task.read")),
            Scope.of(Key.of("test-key1"), ScopeName.of("task.write"))
        );

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);
        RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(
            RequestedScopeNameListToken.of("task.read task.write")
        );

        assertTrue(requestedScopeList.matchesRequestedScopeNameList(requestedScopeNameList));
    }

    @Test
    public void returnFalseIfDoseNotMatchRequestedScopeNameList() {
        List<Scope> scopes = List.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("task.read")),
            Scope.of(Key.of("test-key1"), ScopeName.of("task.write"))
        );

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);
        RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(
            RequestedScopeNameListToken.of("task.read task.write notexist.read")
        );

        assertFalse(requestedScopeList.matchesRequestedScopeNameList(requestedScopeNameList));
    }
}
