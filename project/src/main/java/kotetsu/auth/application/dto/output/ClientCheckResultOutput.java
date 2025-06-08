package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class ClientCheckResultOutput {
    @Getter
    private final boolean isValid;

    private ClientCheckResultOutput(final boolean isValid) {
        this.isValid = isValid;
    }

    public static ClientCheckResultOutput of(final boolean isValid) {
        return new ClientCheckResultOutput(isValid);
    }
}
