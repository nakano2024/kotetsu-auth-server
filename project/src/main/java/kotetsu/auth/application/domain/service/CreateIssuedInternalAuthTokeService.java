package kotetsu.auth.application.domain.service;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.IssuedInternalAuthToken;
import kotetsu.auth.application.domain.entity.PendingInternalAuthToken;
import kotetsu.auth.application.domain.util.IGenerateInternalAuthTokenValudPort;

@Component
public class CreateIssuedInternalAuthTokeService {
    private final IGenerateInternalAuthTokenValudPort generateInternalAuthTokenValudPort;

    public CreateIssuedInternalAuthTokeService(final IGenerateInternalAuthTokenValudPort generateInternalAuthTokenValudPort) {
        this.generateInternalAuthTokenValudPort = generateInternalAuthTokenValudPort;
    }

    public IssuedInternalAuthToken create(final PendingInternalAuthToken pendingInternalAuthToken) {
        return IssuedInternalAuthToken.of(generateInternalAuthTokenValudPort.generate(pendingInternalAuthToken));
    }
}
