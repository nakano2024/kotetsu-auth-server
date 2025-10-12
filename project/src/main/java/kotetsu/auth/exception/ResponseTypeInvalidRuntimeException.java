package kotetsu.auth.exception;

public class ResponseTypeInvalidRuntimeException extends RuntimeException {
    public ResponseTypeInvalidRuntimeException() {
        super("指定されたresponse_typeが不適切です。");
    }
}
