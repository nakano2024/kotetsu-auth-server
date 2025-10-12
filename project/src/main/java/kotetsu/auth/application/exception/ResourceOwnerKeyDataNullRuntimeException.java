package kotetsu.auth.application.exception;

public class ResourceOwnerKeyDataNullRuntimeException extends RuntimeException {
    public ResourceOwnerKeyDataNullRuntimeException() {
        super("RequestedScopeListはnullが許容されません。");
    }
}
