package kotetsu.auth.application.domain.value;

import java.util.Date;

import lombok.Getter;

public class IssuedAt {
    @Getter
    private final Date value;

    private IssuedAt(final Date value) {
        this.value = value;
    }

    public static IssuedAt of(final Date value) {
        IssuedAt issuedAt = new IssuedAt(value);
        return issuedAt;
    }

    public Long getUnixSec() {
        return (value.getTime() / 1000);
    }
}
