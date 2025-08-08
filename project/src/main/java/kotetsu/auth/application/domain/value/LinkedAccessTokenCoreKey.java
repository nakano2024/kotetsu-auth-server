package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LinkedAccessTokenCoreKey {
    @Getter
    @NotBlank
    private final String value;

    private LinkedAccessTokenCoreKey(final String value) {
        this.value = value;
    }

    public static LinkedAccessTokenCoreKey of(final String value) {
        final LinkedAccessTokenCoreKey linkedAccessTokenBodyId = new LinkedAccessTokenCoreKey(value);

        return linkedAccessTokenBodyId;
    }
}
