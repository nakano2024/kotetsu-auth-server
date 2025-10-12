package kotetsu.auth.application.exception;

public class InputRefreshTokenNullException extends Exception {
    public InputRefreshTokenNullException() {
        super("RefreshTokenのinputはnullが許容されません。");
    }
}
