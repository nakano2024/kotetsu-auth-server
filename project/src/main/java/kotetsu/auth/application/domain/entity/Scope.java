package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.Code;
import kotetsu.auth.application.domain.value.ScopeName;
import lombok.Getter;

public class Scope {
    @Getter
    private final Code code;

    @Getter
    private final ScopeName name;

    private Scope(final Code code, final ScopeName name) {
        this.code = code;
        this.name = name;
    }

    public static Scope of(final Code code, final ScopeName name) {
        final Scope scope = new Scope(code, name);
        return scope;
    }
}
