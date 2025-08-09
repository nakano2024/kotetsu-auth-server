package kotetsu.auth.application.exception;

public class InputAuthorizationCodeNullException extends Exception {
    public InputAuthorizationCodeNullException() {
        super("AuthorizationCodeのinputはnullが許容されません。");
    }
}
