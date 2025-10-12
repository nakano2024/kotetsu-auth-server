package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AccessTokenValue {
    public static final int LENGTH = 128;

    @Getter
    @NotBlank
    private final String value;

    private AccessTokenValue(final String value) {
        this.value = value;
    }

    public static AccessTokenValue of(final String value) {
        final AccessTokenValue accessTokenValue = new AccessTokenValue(value);

        return accessTokenValue;
    }
}
