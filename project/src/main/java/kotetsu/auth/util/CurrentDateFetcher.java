package kotetsu.auth.util;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;

@Component
public class CurrentDateFetcher implements IFetchCurrentDatePort {
    private final Clock clock;

    public CurrentDateFetcher(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Date fetch() {
        return Date.from(Instant.now(clock));
    }
}
