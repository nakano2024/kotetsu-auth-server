package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class IdTokenAudience {
    @Getter
    private final String value;

    private IdTokenAudience(final String value) {
        this.value = value;
    }

    public static IdTokenAudience of(final String value) {
        final IdTokenAudience audience = new IdTokenAudience(value);
        return audience;
    }
}
