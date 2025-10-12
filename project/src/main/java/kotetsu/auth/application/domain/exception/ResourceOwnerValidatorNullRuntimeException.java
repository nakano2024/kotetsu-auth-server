package kotetsu.auth.application.domain.exception;

public class ResourceOwnerValidatorNullRuntimeException extends RuntimeException {
    public ResourceOwnerValidatorNullRuntimeException() {
        super("ResourceOwnerValidatorはnullが許容されていません。");
    }
}
