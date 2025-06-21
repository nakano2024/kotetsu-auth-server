package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class ScopeData {
    @Getter
    private final UUID code;

    @Getter
    private final String name;

    private ScopeData(final UUID code, final String name) {
        this.code = code;
        this.name = name;
    }

    public static ScopeData of(final UUID code, final String name) {
        return new ScopeData(code, name);
    }
}
