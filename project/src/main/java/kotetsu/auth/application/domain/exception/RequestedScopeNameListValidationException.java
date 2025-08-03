package kotetsu.auth.application.domain.exception;

public class RequestedScopeNameListValidationException extends RuntimeException {
    public RequestedScopeNameListValidationException(final String message) {
        super(message);
    }
}
