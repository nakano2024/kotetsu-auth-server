package kotetsu.auth.application.domain.service;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.util.IGenerateIdTokenValuePort;

@Component
public class CreateIssuedIdTokenService {
    private final IGenerateIdTokenValuePort generateIdTokenValuePort;

    public CreateIssuedIdTokenService(final IGenerateIdTokenValuePort generateIdTokenValuePort) {
        this.generateIdTokenValuePort = generateIdTokenValuePort;
    }

    public IssuedIdToken create(final IssuedIdTokenMeta meta, final ExistingIdTokenCore idTokenCore) {
        return IssuedIdToken.of(generateIdTokenValuePort.generate(meta, idTokenCore));
    }
}
