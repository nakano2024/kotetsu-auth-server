package kotetsu.auth.application.exception;

public class PermittedScopeListNullRuntimeException extends RuntimeException {
    public PermittedScopeListNullRuntimeException() {
        super("PermittedScopeListはnullが許容されません。");
    }
}
