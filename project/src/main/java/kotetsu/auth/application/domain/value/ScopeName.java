package kotetsu.auth.application.domain.value;

import java.util.Objects;

import lombok.Getter;

public class ScopeName {
    @Getter
    private final String value;

    private ScopeName(final String value) {
        this.value = value;
    }

    public static ScopeName of(final String value) {
        final ScopeName scopeName = new ScopeName(value);
        return scopeName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        ScopeName anotherScopeName = (ScopeName) object;

        return this.value.equals(anotherScopeName.getValue());
    }
}
