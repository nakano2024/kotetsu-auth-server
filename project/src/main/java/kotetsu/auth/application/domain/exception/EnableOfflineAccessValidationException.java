package kotetsu.auth.application.domain.exception;

public class EnableOfflineAccessValidationException extends RuntimeException {
    public EnableOfflineAccessValidationException(final String message) {
        super(message);
    }
}
