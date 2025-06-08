package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class ScopeData {
    @Getter
    private final UUID code;

    @Getter
    private final String name;

    @Getter
    private final String resourceServerUrl;

    private ScopeData(final UUID code, final String name, final String resourceServerUrl) {
        this.code = code;
        this.name = name;
        this.resourceServerUrl = resourceServerUrl;
    }

    public static ScopeData of(final UUID code, final String name, final String resourceServerUr) {
        return new ScopeData(code, name, resourceServerUr);
    }
}
