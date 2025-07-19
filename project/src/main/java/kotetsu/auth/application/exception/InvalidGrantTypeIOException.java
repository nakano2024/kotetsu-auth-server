package kotetsu.auth.application.exception;

import java.io.IOException;

public class InvalidGrantTypeIOException extends IOException {
    public InvalidGrantTypeIOException(final String message) {
        super(message);
    }
}
