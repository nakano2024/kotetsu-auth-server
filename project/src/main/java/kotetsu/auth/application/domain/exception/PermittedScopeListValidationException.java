package kotetsu.auth.application.domain.exception;

public class PermittedScopeListValidationException extends RuntimeException {
    public PermittedScopeListValidationException(final String message) {
        super(message);
    }
}
