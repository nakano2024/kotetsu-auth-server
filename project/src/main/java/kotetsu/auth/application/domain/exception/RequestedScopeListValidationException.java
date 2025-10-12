package kotetsu.auth.application.domain.exception;

public class RequestedScopeListValidationException extends RuntimeException {
    public RequestedScopeListValidationException(final String message) {
        super(message);
    }
}
