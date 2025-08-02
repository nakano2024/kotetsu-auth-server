package kotetsu.auth.application.domain.exception;

public class AccessTokenDraftValidationException extends RuntimeException {
    public AccessTokenDraftValidationException(final String message) {
        super(message);
    }
}
