package kotetsu.auth.application.domain.entity;

import java.util.Objects;

import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.ScopeName;
import lombok.Getter;

public class Scope {
    @Getter
    private final Id id;

    @Getter
    private final ScopeName name;

    private Scope(final Id id, final ScopeName name) {
        this.id = id;
        this.name = name;
    }

    public static Scope of(final Id id, final ScopeName name) {
        final Scope scope = new Scope(id, name);
        return scope;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (object.getClass() != this.getClass()) {
            return false;
        }

        Scope anotherScope = (Scope) object;

        return this.equals(anotherScope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
