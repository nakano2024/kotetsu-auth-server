package kotetsu.auth.application.exception;

import java.io.IOException;

public class MeProfileNotFoundIOException extends IOException {
    public MeProfileNotFoundIOException() {
        super("MeProfileが見つかりません。");
    }
}
