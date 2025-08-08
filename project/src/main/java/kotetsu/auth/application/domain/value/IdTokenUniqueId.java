package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class IdTokenUniqueId {
    @Getter
    @NotBlank
    private final String value;

    private IdTokenUniqueId(final String value) {
        this.value = value;
    }

    public static IdTokenUniqueId of(final String value) {
        final IdTokenUniqueId idTokenUniqueId = new IdTokenUniqueId(value);
        return idTokenUniqueId;
    }
}
