package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class InternalAuthTokenValue {
    @Getter
    @NotBlank
    private final String value;

    private InternalAuthTokenValue(final String value) {
        this.value = value;
    }

    public static InternalAuthTokenValue of(final String value) {
        final InternalAuthTokenValue internalAuthTokenValue = new InternalAuthTokenValue(value);

        return internalAuthTokenValue;
    }
}
