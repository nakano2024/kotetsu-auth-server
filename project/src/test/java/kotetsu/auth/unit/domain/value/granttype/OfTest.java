package kotetsu.auth.unit.domain.value.granttype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.GrantType;

public class OfTest {
    @Test
    public void objectIsConstructedWithMatchingArguments() {
        GrantType grantType = GrantType.of("authorization_code");

        assertEquals("authorization_code", grantType.getValue());
    }
}