package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class Email {
    @Getter
    private final String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email of(final String value) {
        final Email email = new Email(value);
        return email;
    }
}
