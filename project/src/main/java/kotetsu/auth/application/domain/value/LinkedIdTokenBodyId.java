package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LinkedIdTokenBodyId {
    @Getter
    @NotBlank
    private final String value;

    private LinkedIdTokenBodyId(final String value) {
        this.value = value;
    }

    public static LinkedIdTokenBodyId of(final String value) {
        final LinkedIdTokenBodyId linkedIdTokenBodyId = new LinkedIdTokenBodyId(value);

        return linkedIdTokenBodyId;
    }
}
