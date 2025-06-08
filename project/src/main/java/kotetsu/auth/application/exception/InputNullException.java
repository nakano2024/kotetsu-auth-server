package kotetsu.auth.application.exception;

public class InputNullException extends RuntimeException {
    public InputNullException() {
        super("inputはnullが許容されていません。");
    }
}
