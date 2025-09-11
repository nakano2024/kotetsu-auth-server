package kotetsu.auth.unit.domain.entity.issuedidtoken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.value.IdTokenValue;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        IssuedIdToken issuedIdToken = IssuedIdToken.of(
            IdTokenValue.of("test-id-token-value")
        );

        assertEquals("test-id-token-value", issuedIdToken.getValue().getValue());
    }
}