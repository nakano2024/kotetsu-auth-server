package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class Subject {
    @Getter
    private final String value;

    private Subject(final String value) {
        this.value = value;
    }

    public static Subject of(final String value) {
        final Subject subject = new Subject(value);
        return subject;
    }
}
