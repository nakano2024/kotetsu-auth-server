package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LinkedIdTokenCoreKey {
    @Getter
    @NotBlank
    private final String value;

    private LinkedIdTokenCoreKey(final String value) {
        this.value = value;
    }

    public static LinkedIdTokenCoreKey of(final String value) {
        final LinkedIdTokenCoreKey linkedIdTokenBodyId = new LinkedIdTokenCoreKey(value);

        return linkedIdTokenBodyId;
    }
}
