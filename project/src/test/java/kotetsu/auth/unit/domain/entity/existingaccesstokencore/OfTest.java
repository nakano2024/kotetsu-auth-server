package kotetsu.auth.unit.domain.entity.existingaccesstokencore;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.RequestedRelatedAudienceList;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.domain.value.Subject;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RequestedScopeList requestedScopeList = RequestedScopeList.of(List.of(
            Scope.of(
                Key.of("e389027d-9285-86d8-cf34-5dfd8d0b5557"),
                ScopeName.of("task.read")
            )
        ));

        RequestedRelatedAudienceList relatedAudienceList = RequestedRelatedAudienceList.of(
            List.of("https://task.com")
        );

        ExistingAccessTokenCore existingAccessTokenCore = ExistingAccessTokenCore.of(
            Key.of("59085ab5-b68b-5be2-2bf0-71608e4cae3e"),
            Issuer.of("https://issuer.com"),
            Subject.of("f5323e50-a6a0-1442-e88f-b67bb6344183"),
            requestedScopeList,
            relatedAudienceList
        );
        
        assertEquals("59085ab5-b68b-5be2-2bf0-71608e4cae3e", existingAccessTokenCore.getKey().getValue());
        assertEquals("https://issuer.com", existingAccessTokenCore.getIssuer().getValue());
        assertEquals("f5323e50-a6a0-1442-e88f-b67bb6344183", existingAccessTokenCore.getSubject().getValue());
        // RequestedScopeListやRequestedRelatedAudienceListに含まれる値の妥当性は、専用のテストで行うため省略
        // ひとまず引数で渡された値の妥当性を確認
        assertSame(requestedScopeList, existingAccessTokenCore.getScopeList());
        assertSame(relatedAudienceList, existingAccessTokenCore.getRelatedAudienceList());
    }
}
