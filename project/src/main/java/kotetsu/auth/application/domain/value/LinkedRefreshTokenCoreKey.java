package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LinkedRefreshTokenCoreKey {
    @Getter
    @NotBlank
    private final String value;

    private LinkedRefreshTokenCoreKey(final String value) {
        this.value = value;
    }

    public static LinkedRefreshTokenCoreKey of(final String value) {
        final LinkedRefreshTokenCoreKey linkedRefreshTokenBodyId = new LinkedRefreshTokenCoreKey(value);

        return linkedRefreshTokenBodyId;
    }
}
