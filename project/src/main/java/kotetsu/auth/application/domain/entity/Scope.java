package kotetsu.auth.application.domain.entity;

import java.util.Objects;

import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
import lombok.Getter;

public class Scope {
    public static final String KEY_OPENID = "2dd0280c-300a-e8ad-dbc5-db202beb34a0";
    public static final String NAME_OPENID = "openid";

    @Getter
    private final Key key;

    @Getter
    private final ScopeName name;

    private Scope(final Key key, final ScopeName name) {
        this.key = key;
        this.name = name;
    }

    public static Scope of(final Key key, final ScopeName name) {
        final Scope scope = new Scope(key, name);
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

        return (
            this.getKey().equals(anotherScope.getKey()) &&
            this.getName().equals(anotherScope.getName())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name);
    }
}
