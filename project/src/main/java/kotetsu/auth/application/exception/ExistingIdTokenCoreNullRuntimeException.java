package kotetsu.auth.application.exception;

public class ExistingIdTokenCoreNullRuntimeException extends RuntimeException {
    public ExistingIdTokenCoreNullRuntimeException() {
        super("ExistingAccessTokenCoreはnullが許容されません。");
    }
}
