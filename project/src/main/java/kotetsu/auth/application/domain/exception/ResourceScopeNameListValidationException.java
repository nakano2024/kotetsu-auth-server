package kotetsu.auth.application.domain.exception;

public class ResourceScopeNameListValidationException extends RuntimeException {
    public ResourceScopeNameListValidationException(final String message) {
        super(message);
    }
}
