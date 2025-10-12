package kotetsu.auth.application.domain.exception;

public class LinkedAccessTokenCoreKeyValidationRuntimeException extends RuntimeException {
    public LinkedAccessTokenCoreKeyValidationRuntimeException(final String message) {
        super(message);
    }
}
