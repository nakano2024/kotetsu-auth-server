package kotetsu.auth.application.domain.exception;

public class IssuedAccessTokenValidationRuntimeException extends RuntimeException {
    public IssuedAccessTokenValidationRuntimeException(final String message) {
        super(message);
    }
}
