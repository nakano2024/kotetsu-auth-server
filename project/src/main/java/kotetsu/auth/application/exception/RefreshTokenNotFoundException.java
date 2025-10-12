package kotetsu.auth.application.exception;

public class RefreshTokenNotFoundException extends Exception {
    public RefreshTokenNotFoundException() {
        super("RefreshTokenが見つかりません。");
    }
}
