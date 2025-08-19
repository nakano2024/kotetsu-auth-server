package kotetsu.auth.util;

import java.util.UUID;

import kotetsu.auth.application.domain.util.IGenerateUuidPort;

public class UuidGenerator implements IGenerateUuidPort {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
    
}
