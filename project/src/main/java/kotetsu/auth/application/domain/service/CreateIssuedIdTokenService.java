package kotetsu.auth.application.domain.service;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.entity.IdTokenMeta;
import kotetsu.auth.application.domain.entity.IssuedIdToken;
import kotetsu.auth.application.domain.util.IGenerateIdTokenValuePort;

public class CreateIssuedIdTokenService {
    private final IGenerateIdTokenValuePort generateIdTokenValuePort;

    public CreateIssuedIdTokenService(final IGenerateIdTokenValuePort generateIdTokenValuePort) {
        this.generateIdTokenValuePort = generateIdTokenValuePort;
    }

    public IssuedIdToken create(IdTokenMeta meta, ExistingIdTokenCore core) {
        return IssuedIdToken.of(generateIdTokenValuePort.generate(meta, core));
    }
}
