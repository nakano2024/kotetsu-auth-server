package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class RefreshTokenValue {
    public static final int LENGTH = 128;

    @Getter
    @NotBlank
    private final String value;

    private RefreshTokenValue(final String value) {
        this.value = value;
    }

    public static RefreshTokenValue of(final String value) {
        final RefreshTokenValue refreshTokenValue = new RefreshTokenValue(value);

        return refreshTokenValue;
    }
}
