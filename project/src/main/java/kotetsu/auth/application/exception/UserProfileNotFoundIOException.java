package kotetsu.auth.application.exception;

import java.io.IOException;

public class UserProfileNotFoundIOException extends IOException {
    public UserProfileNotFoundIOException() {
        super("UserProfile Not Found");
    }
}
