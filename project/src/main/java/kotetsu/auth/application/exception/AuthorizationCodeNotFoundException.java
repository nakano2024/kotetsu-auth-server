package kotetsu.auth.application.exception;

public class AuthorizationCodeNotFoundException extends Exception {
    public AuthorizationCodeNotFoundException() {
        super("認可コードが見つかりません。");
    }
}
