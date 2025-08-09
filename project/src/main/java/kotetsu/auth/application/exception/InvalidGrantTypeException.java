package kotetsu.auth.application.exception;

public class InvalidGrantTypeException extends Exception {
    public InvalidGrantTypeException() {
        super("無効なgrantTypeです。");
    }
}
