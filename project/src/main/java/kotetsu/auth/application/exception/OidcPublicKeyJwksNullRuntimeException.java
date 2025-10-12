package kotetsu.auth.application.exception;

public class OidcPublicKeyJwksNullRuntimeException extends RuntimeException {
    public OidcPublicKeyJwksNullRuntimeException() {
        super("OidcPublicKeyJwksはNULLが許容されません。");
    }
}
