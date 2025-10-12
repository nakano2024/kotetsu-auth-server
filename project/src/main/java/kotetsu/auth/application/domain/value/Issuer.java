package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class Issuer {
    @Getter
    private final String value;

    private Issuer(final String value) {
        this.value = value;
    }

    public static Issuer of(final String value) {
        final Issuer issuer = new Issuer(value);
        return issuer;
    }
}
