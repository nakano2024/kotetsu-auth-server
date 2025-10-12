package kotetsu.auth.unit.domain.value.granttype;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import kotetsu.auth.application.domain.value.GrantType;

public class IsRefreshTokenTest {
    @Test
    public void returnTrueIfValueIsRefreshToken() {
        GrantType grantType = GrantType.of(GrantType.REFRESH_TOKEN);

        assertTrue(grantType.isRefreshToken());
    }

    @Test
    public void returnFalseIfValueIsNotRefreshToken() {
        GrantType grantType = GrantType.of(GrantType.AUTORIZATION_CODE);

        assertFalse(grantType.isRefreshToken());
    }
}
