package kotetsu.auth.application.exception;

public class AuthorizationCodeExpiredException extends Exception {
    public AuthorizationCodeExpiredException() {
        super("認可コードの期限が切れています。");
    }
}
