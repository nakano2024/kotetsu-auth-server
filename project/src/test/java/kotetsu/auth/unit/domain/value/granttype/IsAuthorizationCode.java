package kotetsu.auth.unit.domain.value.granttype;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.GrantType;

public class IsAuthorizationCode {
    @Test
    public void returnTrueIfValueIsAuthorizationCode() {
        GrantType grantType = GrantType.of(GrantType.AUTORIZATION_CODE);

        assertTrue(grantType.isAuthorizationCode());
    }

    @Test
    public void returnFalseIfValueIsNotAuthorizationCode() {
        GrantType grantType = GrantType.of(GrantType.REFRESH_TOKEN);

        assertFalse(grantType.isAuthorizationCode());
    }
}
