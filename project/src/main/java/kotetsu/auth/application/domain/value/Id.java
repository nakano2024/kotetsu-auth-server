package kotetsu.auth.application.domain.value;

import java.util.Objects;

import lombok.Getter;

public class Id {
    @Getter
    private final String value;

    private Id(final String value) {
        this.value = value;
    }

    public static Id of(final String value) {
        final Id code = new Id(value);
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

        Id anotherCode = (Id) objct;
        return this.equals(anotherCode);
    }
}
