package kotetsu.auth.application.exception;

public class TokenGrantTypeDoseNotMatchException extends Exception {
    public TokenGrantTypeDoseNotMatchException() {
        super("TokenGrantTypeが当該認可フローと一致しません。");
    }
}
