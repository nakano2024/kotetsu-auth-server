package kotetsu.auth.application.exception;

public class RefreshTokenExpiredException extends Exception {
    public RefreshTokenExpiredException() {
        super("RefreshTokenの期限が切れています。");
    }
}
