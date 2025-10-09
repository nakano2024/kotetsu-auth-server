package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.ScopeDescription;
import kotetsu.auth.application.domain.value.ScopeName;
import lombok.Getter;

public class ScopeInformation {
    @Getter
    private final ScopeName name;

    @Getter
    private final ScopeDescription description;

    private ScopeInformation(final ScopeName name, final ScopeDescription description) {
        this.name = name;
        this.description = description;
    }

    public static ScopeInformation of(final ScopeName name, final ScopeDescription description) {
        final ScopeInformation scopeInformation = new ScopeInformation(name, description);
        return scopeInformation;
    }
}
