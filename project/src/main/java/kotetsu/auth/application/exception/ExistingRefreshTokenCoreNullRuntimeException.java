package kotetsu.auth.application.exception;

public class ExistingRefreshTokenCoreNullRuntimeException extends RuntimeException {
    public ExistingRefreshTokenCoreNullRuntimeException() {
        super("ExistingRefreshTokenCoreはnullが許容されません。");
    }
}
