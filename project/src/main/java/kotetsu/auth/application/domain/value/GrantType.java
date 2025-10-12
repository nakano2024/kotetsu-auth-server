package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class GrantType {
    public static final String AUTORIZATION_CODE = "authorization_code";
    public static final String REFRESH_TOKEN = "refresh_token";

    @Getter
    private final String value;

    private GrantType(final String value) {
        this.value = value;
    }

    public static GrantType of(final String value) {
        final GrantType grantType = new GrantType(value);

        return grantType;
    }

    public boolean isRefreshToken() {
        return value.equals(REFRESH_TOKEN);
    }

    public boolean isAuthorizationCode() {
        return value.equals(AUTORIZATION_CODE);
    }
}
