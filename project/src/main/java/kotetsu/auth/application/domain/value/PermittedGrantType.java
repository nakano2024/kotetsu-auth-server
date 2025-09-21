package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class PermittedGrantType {
    @Getter
    private final String value;

    private PermittedGrantType(final String value) {
        this.value = value;
    }

    public static PermittedGrantType of(final String value) {
        final PermittedGrantType permittedGrantType = new PermittedGrantType(value);

        return permittedGrantType;
    }
}
