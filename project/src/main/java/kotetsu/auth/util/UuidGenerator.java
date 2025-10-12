package kotetsu.auth.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IGenerateUuidPort;

@Component
public class UuidGenerator implements IGenerateUuidPort {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
    
}
