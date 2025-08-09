package kotetsu.auth.application.exception;

public class ExistingAccessTokenCoreNullRuntimeException extends RuntimeException {
    public ExistingAccessTokenCoreNullRuntimeException() {
        super("ExistingAccessTokenCoreはnullが許容されません。");
    }
}
