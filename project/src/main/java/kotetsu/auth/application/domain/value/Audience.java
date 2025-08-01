package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class Audience {
    @Getter
    private final String value;

    private Audience(final String value) {
        this.value = value;
    }

    public static Audience of(final String value) {
        final Audience audience = new Audience(value);
        return audience;
    }
}
