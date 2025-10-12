package kotetsu.auth.application.exception;

public class UserCredentialNotFoundException extends Exception {
    public UserCredentialNotFoundException() {
        super("UserCredentialが見つかりません。");
    }
}
