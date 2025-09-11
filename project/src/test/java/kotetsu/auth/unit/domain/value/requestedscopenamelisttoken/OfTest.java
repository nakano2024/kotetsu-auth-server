package kotetsu.auth.unit.domain.value.requestedscopenamelisttoken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        RequestedScopeNameListToken requestedScopeNameListToken = RequestedScopeNameListToken.of("read write");

        assertEquals("read write", requestedScopeNameListToken.getValue());
    }
}