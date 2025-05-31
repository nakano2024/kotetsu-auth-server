package kotetsu.auth.application.domain.exception;

import java.io.IOException;

public class UserProfileNotFoundException extends IOException {
    public UserProfileNotFoundException() {
        super("UserProfile Not Found");
    }
}
