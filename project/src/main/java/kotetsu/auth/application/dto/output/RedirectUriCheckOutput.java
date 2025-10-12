package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class RedirectUriCheckOutput {
    @Getter
    private final boolean isValid;

    private RedirectUriCheckOutput(final boolean isValid) {
        this.isValid = isValid;
    }

    public static RedirectUriCheckOutput of(final boolean isValid) {
        final RedirectUriCheckOutput output = new RedirectUriCheckOutput(isValid);

        return output;
    }
}
