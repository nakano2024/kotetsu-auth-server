package kotetsu.auth.application.exception;

public class RequesterClientNotFoundRuntimeException extends Exception {
    public RequesterClientNotFoundRuntimeException() {
        super("RequesterClientが見つかりません。");
    }
}
