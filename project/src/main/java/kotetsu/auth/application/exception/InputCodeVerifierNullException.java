package kotetsu.auth.application.exception;

public class InputCodeVerifierNullException extends Exception {
    public InputCodeVerifierNullException() {
        super("CodeVerifierInputはnullが許容されません。");
    }
}
