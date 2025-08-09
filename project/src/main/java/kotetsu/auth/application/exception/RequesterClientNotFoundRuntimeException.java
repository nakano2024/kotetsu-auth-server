package kotetsu.auth.application.exception;

public class RequesterClientNotFoundRuntimeException extends RuntimeException {
    public RequesterClientNotFoundRuntimeException() {
        super("RequesterClientが見つかりません。");
    }
}
