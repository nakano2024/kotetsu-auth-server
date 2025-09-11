package kotetsu.auth.application.domain.value;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class Key {
    @Getter
    @NotBlank
    private final String value;

    private Key(final String value) {
        this.value = value;
    }

    public static Key of(final String value) {
        final Key code = new Key(value);
        return code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object objct) {
        if (objct == null) {
            return false;
        }

        if (objct == this) {
            return true;
        }

        if (objct.getClass() != this.getClass()) {
            return false;
        }

        Key anotherCode = (Key) objct;
        return this.value.equals(anotherCode.getValue());
    }
}
