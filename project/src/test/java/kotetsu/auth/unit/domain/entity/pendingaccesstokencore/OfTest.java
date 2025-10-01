package kotetsu.auth.unit.domain.entity.pendingaccesstokencore;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.ClientId;
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
        ClientId requesterClientId = ClientId.of("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com");

        PendingAccessTokenCore pendingAccessTokenCore = PendingAccessTokenCore.of(
            key,
            issuer,
            subject,
            requestedScopeList,
            requesterClientId
        );

        assertEquals("test-key", pendingAccessTokenCore.getKey().getValue());
        assertEquals("test-issuer", pendingAccessTokenCore.getIssuer().getValue());
        assertEquals("test-subject", pendingAccessTokenCore.getSubject().getValue());
        assertSame(requestedScopeList, pendingAccessTokenCore.getRequestedScopeList());
        assertEquals("30aa6868-ef8d-9508-6759-b8c808087687.kotetsu.com", pendingAccessTokenCore.getRequesterClientId().getValue());
    }
}