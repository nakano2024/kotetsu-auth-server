package kotetsu.auth.application.domain.value;

import java.util.Objects;

import lombok.Getter;

public class Code {
    @Getter
    private final String value;

    private Code(final String value) {
        this.value = value;
    }

    public static Code of(final String value) {
        final Code code = new Code(value);
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

        Code anotherCode = (Code) objct;
        return this.equals(anotherCode);
    }
}
