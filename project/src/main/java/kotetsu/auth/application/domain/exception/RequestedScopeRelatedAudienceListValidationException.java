package kotetsu.auth.application.domain.exception;

public class RequestedScopeRelatedAudienceListValidationException extends RuntimeException {
    public RequestedScopeRelatedAudienceListValidationException(final String message) {
        super(message);
    }
}
