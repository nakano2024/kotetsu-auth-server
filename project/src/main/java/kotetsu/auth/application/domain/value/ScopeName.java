package kotetsu.auth.application.domain.value;

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
}
