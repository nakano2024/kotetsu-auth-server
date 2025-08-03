package kotetsu.auth.application.domain.exception;

public class AccessTokenPendingValidationException extends RuntimeException {
    public AccessTokenPendingValidationException(final String message) {
        super(message);
    }
}
