package kotetsu.auth.application.exception;

public class InvalidPendingScopesIOException extends RuntimeException {
    public InvalidPendingScopesIOException(final String message) {
        super(message);
    }
}
