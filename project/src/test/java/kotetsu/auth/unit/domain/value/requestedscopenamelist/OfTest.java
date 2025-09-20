package kotetsu.auth.unit.domain.value.requestedscopenamelist;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.ScopeName;

public class OfTest {
    public static Stream<Arguments> getValidScopeTokens() {
        return Stream.of(
            Arguments.of(
                "task.read task.write",
                Set.of(
                    ScopeName.of("task.read"),
                    ScopeName.of("task.write")
                )
            ),
            Arguments.of(
                "task.read",
                Set.of(
                    ScopeName.of("task.read")
                )
            ),
            Arguments.of(
                "openid",
                Set.of(
                    ScopeName.of("openid")
                )
            )
        );
    }

    @MethodSource("getValidScopeTokens")
    @ParameterizedTest
    public void objectIsConstructedWithMatchingArguments(String validScopeToken, Set<ScopeName> expectedScopeSet) {
        RequestedScopeNameListToken token = RequestedScopeNameListToken.of(validScopeToken);
        RequestedScopeNameList requestedScopeNameList = RequestedScopeNameList.of(token);

        final Set<ScopeName> expectedScopes = expectedScopeSet;

        assertEquals(expectedScopes, requestedScopeNameList.getValue());
    }
}
