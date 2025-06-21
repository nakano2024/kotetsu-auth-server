package kotetsu.auth.application.exception;

public class AuthorizationCodeExpiredIOException extends RuntimeException {
    public AuthorizationCodeExpiredIOException() {
        super();
    }

    public AuthorizationCodeExpiredIOException(final String message) {
        super(message);
    }
}