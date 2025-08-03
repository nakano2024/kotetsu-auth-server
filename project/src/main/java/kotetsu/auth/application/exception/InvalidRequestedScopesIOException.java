package kotetsu.auth.application.exception;

public class InvalidRequestedScopesIOException extends RuntimeException {
    public InvalidRequestedScopesIOException(final String message) {
        super(message);
    }
}
