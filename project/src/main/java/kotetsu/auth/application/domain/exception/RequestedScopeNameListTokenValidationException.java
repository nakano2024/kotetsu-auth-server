package kotetsu.auth.application.domain.exception;

public class RequestedScopeNameListTokenValidationException extends RuntimeException {
    public RequestedScopeNameListTokenValidationException(final String message) {
        super(message);
    }
}
