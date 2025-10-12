package kotetsu.auth.application.domain.value;

import java.util.Date;

import lombok.Getter;

public class ExpiredAt {
    @Getter
    private final Date value;

    private ExpiredAt(final Date value) {
        this.value = value;
    }

    public static ExpiredAt of(final Date value) {
        ExpiredAt expiredAt = new ExpiredAt(value);
        return expiredAt;
    }

    public boolean hasExpired(final Date today) {
        return value.before(today);
    }

    public Long getUnixSec() {
        return (value.getTime() / 1000);
    }
}
