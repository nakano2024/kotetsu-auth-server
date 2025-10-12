package kotetsu.auth.unit.domain.entity.issuedinternalauthtoken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.IssuedInternalAuthToken;
import kotetsu.auth.application.domain.value.InternalAuthTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        IssuedInternalAuthToken issuedInternalAuthToken = IssuedInternalAuthToken.of(
            InternalAuthTokenValue.of("test-internal-token-value")
        );

        assertEquals("test-internal-token-value", issuedInternalAuthToken.getValue().getValue());
    }
}