package kotetsu.auth.application.exception;

public class InvalidCodeVerifierException extends Exception {
    public InvalidCodeVerifierException() {
        super("CodeVerifilerの値が不適切です。");
    }
}
