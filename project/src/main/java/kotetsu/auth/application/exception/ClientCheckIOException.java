package kotetsu.auth.application.exception;

import java.io.IOException;

public class ClientCheckIOException extends IOException {
    public ClientCheckIOException(final String message) {
        super(message);
    }
}
