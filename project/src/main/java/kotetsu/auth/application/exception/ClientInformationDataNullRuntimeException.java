package kotetsu.auth.application.exception;

public class ClientInformationDataNullRuntimeException extends RuntimeException {
    public ClientInformationDataNullRuntimeException() {
        super("ClientInformationDataはnullが許容されていません。");
    }
}
