package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LinkedRefreshTokenBodyId {
    @Getter
    @NotBlank
    private final String value;

    private LinkedRefreshTokenBodyId(final String value) {
        this.value = value;
    }

    public static LinkedRefreshTokenBodyId of(final String value) {
        final LinkedRefreshTokenBodyId linkedRefreshTokenBodyId = new LinkedRefreshTokenBodyId(value);

        return linkedRefreshTokenBodyId;
    }
}
