package kotetsu.auth.application.exception;

import java.io.IOException;

public class UserProfileNotFoundException extends IOException {
    public UserProfileNotFoundException() {
        super("UserProfile Not Found");
    }
}
