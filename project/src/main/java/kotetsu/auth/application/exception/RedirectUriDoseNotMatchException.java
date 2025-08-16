package kotetsu.auth.application.exception;

public class RedirectUriDoseNotMatchException extends Exception {
    public RedirectUriDoseNotMatchException() {
        super("redirectUriが一致しません。");
    }
}
