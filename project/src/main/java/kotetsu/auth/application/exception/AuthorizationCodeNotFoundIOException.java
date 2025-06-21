package kotetsu.auth.application.exception;

public class AuthorizationCodeNotFoundIOException extends RuntimeException {
    public AuthorizationCodeNotFoundIOException() {
        super();
    }

    public AuthorizationCodeNotFoundIOException(final String message) {
        super(message);
    }
}