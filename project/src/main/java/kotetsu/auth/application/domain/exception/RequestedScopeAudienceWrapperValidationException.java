package kotetsu.auth.application.domain.exception;

public class RequestedScopeAudienceWrapperValidationException extends RuntimeException {
    public RequestedScopeAudienceWrapperValidationException(final String message) {
        super(message);
    }
}
