package kotetsu.auth.application.exception;

public class RequestedScopeListNullRuntimeException extends RuntimeException {
    public RequestedScopeListNullRuntimeException() {
        super("RequestedScopeListはnullが許容されません。");
    }
}
