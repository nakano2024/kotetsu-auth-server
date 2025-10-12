package kotetsu.auth.application.exception;

public class InputNullRuntimeException extends RuntimeException {
    public InputNullRuntimeException() {
        super("inputはnullが許容されていません。");
    }
}
