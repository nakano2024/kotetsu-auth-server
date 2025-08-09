package kotetsu.auth.application.domain.service;

import kotetsu.auth.application.domain.entity.IssuedInternalAuthToken;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.util.IGenerateInternalAuthTokenValudPort;

public class CreateIssuedInternalAuthTokeService {
    private final IGenerateInternalAuthTokenValudPort generateInternalAuthTokenValudPort;

    public CreateIssuedInternalAuthTokeService(final IGenerateInternalAuthTokenValudPort generateInternalAuthTokenValudPort) {
        this.generateInternalAuthTokenValudPort = generateInternalAuthTokenValudPort;
    }

    public IssuedInternalAuthToken create(final PendingInternalAuthToken pendingInternalAuthToken) {
        return IssuedInternalAuthToken.of(generateInternalAuthTokenValudPort.generate(pendingInternalAuthToken));
    }
}
