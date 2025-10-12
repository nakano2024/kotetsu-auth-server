package kotetsu.auth.application.exception;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException() {
        super("Client Not Found");
    }
}
