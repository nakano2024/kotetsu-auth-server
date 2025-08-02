package kotetsu.auth.application.domain.exception;

public class EnableOpenIdValidationException extends RuntimeException {
    public EnableOpenIdValidationException(final String message) {
        super(message);
    }
}
