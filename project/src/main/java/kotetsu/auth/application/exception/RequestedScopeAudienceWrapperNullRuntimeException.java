package kotetsu.auth.application.exception;

public class RequestedScopeAudienceWrapperNullRuntimeException extends RuntimeException {
    public RequestedScopeAudienceWrapperNullRuntimeException() {
        super("RequestedScopeAudienceWrappeはnullが許容されません。");
    }
}
