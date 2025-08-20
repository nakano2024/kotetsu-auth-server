package kotetsu.auth.exception;

public class Sha256ConvertRuntimeException extends RuntimeException {
    public Sha256ConvertRuntimeException() {
        super("SHA256ハッシュ化に失敗しました。");
    }
}
