package kotetsu.auth.application.exception;

public class ExistingIdTokenMetaNullRuntimeException extends RuntimeException {
    public ExistingIdTokenMetaNullRuntimeException() {
        super("ExistingIdTokenMetaはnullが許容されていません。");
    }
}
