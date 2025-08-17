package kotetsu.auth.application.exception;

public class RequestedScopeListNullRuntimeException extends RuntimeException {
    public RequestedScopeListNullRuntimeException() {
        super("RequestedScopeAudienceWrappeはnullが許容されません。");
    }
}
