package kotetsu.auth.application.domain.exception;

public class ClientIdValidationRuntimeException extends RuntimeException {
    public ClientIdValidationRuntimeException(final String message) {
        super(message);
    }
}
