package kotetsu.auth.application.exception;

public class ClientCredentialDataNullException extends RuntimeException {
    public ClientCredentialDataNullException() {
        super("ClientCredentialDataはnullが許容されていません。");
    }
}
