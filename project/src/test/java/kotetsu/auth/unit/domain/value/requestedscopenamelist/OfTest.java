package kotetsu.auth.unit.domain.value.requestedscopenamelist;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.ScopeName;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RequestedScopeNameListToken token = RequestedScopeNameListToken.of("task.read task.write");
        RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(token);

        final Set<ScopeName> expectedScopes = Set.of(
            ScopeName.of("task.read"),
            ScopeName.of("task.write")
        );

        assertEquals(expectedScopes, requestedScopeNameList.getValue());
    }

    @Test
    public void objecIsConstructedWithEmptyScopes() {
        RequestedScopeNameListToken token = RequestedScopeNameListToken.of("");
        RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(token);

        final Set<ScopeName> expectedScopes = Set.of(
            ScopeName.of("")
        );

        assertEquals(expectedScopes, requestedScopeNameList.getValue());
    }
}
