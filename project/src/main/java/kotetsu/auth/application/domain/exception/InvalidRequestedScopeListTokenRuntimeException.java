package kotetsu.auth.application.domain.exception;

public class InvalidRequestedScopeListTokenRuntimeException extends RuntimeException {
    public InvalidRequestedScopeListTokenRuntimeException() {
        super("RequestedScopeListTokenの形式が不正です。");
    }
}
