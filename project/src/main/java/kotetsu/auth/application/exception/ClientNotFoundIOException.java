package kotetsu.auth.application.exception;

import java.io.IOException;

public class ClientNotFoundIOException extends IOException {
    public ClientNotFoundIOException() {
        super("Client Not Found");
    }
}
