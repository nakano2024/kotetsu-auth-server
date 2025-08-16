package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class UserName {
    @Getter
    private final String value;

    private UserName(final String value) {
        this.value = value;
    }

    public static UserName of(final String value) {
        final UserName userName = new UserName(value);
        return userName;
    }
}
