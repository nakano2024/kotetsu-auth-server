package kotetsu.auth.util;

import java.time.Instant;
import java.util.Date;

import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;

public class CurrentDateFetcher implements IFetchCurrentDatePort {
    @Override
    public Date fetch() {
        final Instant currentInstant = Instant.now();
        return Date.from(currentInstant);
    }
}
