package kotetsu.auth.application.domain.value;

import java.util.Date;

public class IssuedAt {
    private final Date value;

    private IssuedAt(final Date value) {
        this.value = value;
    }

    public static IssuedAt of(final Date value) {
        IssuedAt issuedAt = new IssuedAt(value);
        return issuedAt;
    }

    public Long getUnixTime() {
        return (value.getTime() / 1000);
    }
}
