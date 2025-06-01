package kotetsu.auth.util;

import java.time.Instant;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.util.IGetCurrentInstantPort;

@Component
public class CurrentInstantGetter implements IGetCurrentInstantPort {
    @Override
    public Instant getCurrent() {
        return Instant.now();
    }
}
