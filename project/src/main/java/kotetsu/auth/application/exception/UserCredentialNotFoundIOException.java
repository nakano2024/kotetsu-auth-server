package kotetsu.auth.application.exception;

import java.io.IOException;

public class UserCredentialNotFoundIOException extends IOException {
    public UserCredentialNotFoundIOException() {
        super("UserCredential Not Found");
    }
}
