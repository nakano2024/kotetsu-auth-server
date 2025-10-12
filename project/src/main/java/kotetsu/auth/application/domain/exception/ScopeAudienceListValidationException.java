package kotetsu.auth.application.domain.exception;

public class ScopeAudienceListValidationException extends RuntimeException {
    public ScopeAudienceListValidationException(final String message) {
        super(message);
    }
}
