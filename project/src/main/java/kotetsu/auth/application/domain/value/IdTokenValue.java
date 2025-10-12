package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class IdTokenValue {
    @Getter
    @NotBlank
    private final String value;

    private IdTokenValue(final String value) {
        this.value = value;
    }

    public static IdTokenValue of(final String value) {
        final IdTokenValue idTokenValue = new IdTokenValue(value);

        return idTokenValue;
    }
}
