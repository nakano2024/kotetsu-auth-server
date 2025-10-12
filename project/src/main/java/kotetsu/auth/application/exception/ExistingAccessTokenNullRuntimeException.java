package kotetsu.auth.application.exception;

public class ExistingAccessTokenNullRuntimeException extends RuntimeException {
    public ExistingAccessTokenNullRuntimeException() {
        super("ExistingAccessTokenはnullが許容されません。");
    }
}
