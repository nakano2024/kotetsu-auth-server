package kotetsu.auth.application.exception;

public class ClientCredentialNotFoundException extends Exception {
    public ClientCredentialNotFoundException() {
        super("ClientCredentialが見つかりません。");
    }
}
