package kotetsu.auth.unit.domain.entity.requestedscopelist;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;

public class HasOpenidTest {
    @Test
    public void returnTrueIfOpenidContaind() {
        List<Scope> scopes = List.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read")),
            Scope.of(Key.of(Scope.KEY_OPENID), ScopeName.of(Scope.NAME_OPENID))
        );

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);

        assertTrue(requestedScopeList.hasOpenid());
    }

    @Test
    public void returnFalseIfOpenidNotContaind() {
        List<Scope> scopes = List.of(
            Scope.of(Key.of("test-key1"), ScopeName.of("read"))
        );

        RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);

        assertFalse(requestedScopeList.hasOpenid());
    }
}
