package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class Code {
    @Getter
    private final String value;

    private Code(final String value) {
        this.value = value;
    }

    public static Code of(final String value) {
        final Code code = new Code(value);
        return code;
    }
}
