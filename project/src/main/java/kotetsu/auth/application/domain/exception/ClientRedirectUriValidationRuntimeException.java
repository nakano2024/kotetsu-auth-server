package kotetsu.auth.application.domain.exception;

public class ClientRedirectUriValidationRuntimeException extends RuntimeException {
    public ClientRedirectUriValidationRuntimeException(final String message) {
        super(message);
    }
}
