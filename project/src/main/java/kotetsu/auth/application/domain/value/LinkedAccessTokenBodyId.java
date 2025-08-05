package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LinkedAccessTokenBodyId {
    @Getter
    @NotBlank
    private final String value;

    private LinkedAccessTokenBodyId(final String value) {
        this.value = value;
    }

    public static LinkedAccessTokenBodyId of(final String value) {
        final LinkedAccessTokenBodyId linkedAccessTokenBodyId = new LinkedAccessTokenBodyId(value);

        return linkedAccessTokenBodyId;
    }
}
