package kotetsu.auth.unit.domain.entity.pendingaccesstokencore;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.domain.value.Subject;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        Key key = Key.of("test-key");
        Issuer issuer = Issuer.of("test-issuer");
        Subject subject = Subject.of("test-subject");
        RequestedScopeList requestedScopeList = RequestedScopeList.of(List.of(
            Scope.of(Key.of("scope-key"), ScopeName.of("read"))
        ));

        PendingAccessTokenCore pendingAccessTokenCore = PendingAccessTokenCore.of(
            key,
            issuer,
            subject,
            requestedScopeList
        );

        assertEquals("test-key", pendingAccessTokenCore.getKey().getValue());
        assertEquals("test-issuer", pendingAccessTokenCore.getIssuer().getValue());
        assertEquals("test-subject", pendingAccessTokenCore.getSubject().getValue());
        // RequestedScopeListに含まれる値の妥当性は、専用のテストで行うため省略
        // ひとまず引数で渡された値の妥当性を確認
        assertSame(requestedScopeList, pendingAccessTokenCore.getRequestedScopeList());
    }
}