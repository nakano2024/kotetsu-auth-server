package kotetsu.auth.application.domain.value;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class Duration {
    @Getter
    @NotNull
    private final IssuedAt issuedAt;

    @Getter
    @NotNull
    private final ExpiredAt expiredAt;

    private Duration(final IssuedAt issuedAt, final ExpiredAt expiredAt) {
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static Duration of(final IssuedAt issuedAt, final ExpiredAt expiredAt) {
        final Duration duration = new Duration(issuedAt, expiredAt);

        return duration;
    }

    public Long getDifferenceSec() {
        return Math.abs(expiredAt.getUnixSec() - issuedAt.getUnixSec());
    }
}
