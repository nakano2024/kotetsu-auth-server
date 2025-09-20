package kotetsu.auth.application.exception;

public class InvalidScopeNameListTokenException extends Exception {
    public InvalidScopeNameListTokenException() {
        super("存在しないスコープが含まれてます。");
    }
}
