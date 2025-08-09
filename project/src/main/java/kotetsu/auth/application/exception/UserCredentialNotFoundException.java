package kotetsu.auth.application.exception;

public class UserCredentialNotFoundException extends Exception {
    public UserCredentialNotFoundException() {
        super("UserCredential Not Found");
    }
}
