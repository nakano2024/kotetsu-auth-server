package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class Nonce {
    @Getter
    private final String value;

    private Nonce(final String value) {
        this.value = value;
    }

    public static Nonce of(final String value) {
        final Nonce nonce = new Nonce(value);
        return nonce;
    }
}
