package kotetsu.auth.application.exception;

public class AudienceClientNullRuntimeException extends RuntimeException {
    public AudienceClientNullRuntimeException() {
        super("AudienceClientはnullが許容されません。");
    }
}
