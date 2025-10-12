package kotetsu.auth.application.exception;

public class ScopeInformationListNullRuntimeException extends RuntimeException {
    public ScopeInformationListNullRuntimeException() {
        super("ScopeInformationListはnullが許容されません。");
    }
}
