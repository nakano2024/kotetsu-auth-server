package kotetsu.auth.application.domain.value;

public class UserActivation {
    private final boolean value;

    private UserActivation(final boolean value) {
        this.value = value;
    }

    public static UserActivation of(final boolean value) {
        final UserActivation userIsActive = new UserActivation(value);

        return userIsActive;
    }

    public boolean isActive() {
        return value;
    }
}
