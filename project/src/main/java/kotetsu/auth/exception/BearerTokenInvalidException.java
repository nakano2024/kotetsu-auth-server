package kotetsu.auth.exception;

import java.io.IOException;

public class BearerTokenInvalidException extends IOException {
    public BearerTokenInvalidException(String message) {
        super(message);
    }
}
