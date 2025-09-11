package kotetsu.auth.unit.domain.value.requestedscopenamelist;

import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RequestedScopeNameListToken token = RequestedScopeNameListToken.of("read write");
        RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(token);

        // RequestedScopeNameListは内部でバリデーション処理を行うため、
        // オブジェクトが正常に作成されることを確認
        assert requestedScopeNameList != null;
    }
}