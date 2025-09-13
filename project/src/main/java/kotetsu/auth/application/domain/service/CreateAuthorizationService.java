package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.util.IGenerateAuthorizationCodeValuePort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

public class CreateAuthorizationService {

    private final IGenerateAuthorizationCodeValuePort fetchGenerateAuthorizationCodeValuePort;

    public CreateAuthorizationService(
        final IGenerateAuthorizationCodeValuePort fetchGenerateAuthorizationCodeValuePort
    ) {
        this.fetchGenerateAuthorizationCodeValuePort = fetchGenerateAuthorizationCodeValuePort;
    }

    public RequestedAuthorization create(
        final AuthorizationCodeChallenge challenge,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreId,
        final LinkedIdTokenCoreKey linkedIdTokenCoreId,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreId,
        final IssuedAt issuedAt
    ) {
        final AuthorizationCodeValue codeValue = fetchGenerateAuthorizationCodeValuePort.generate();
        return RequestedAuthorization.of(
            AuthorizationCode.of(
                codeValue,
                challenge,
                ExpiredAt.of(Date.from(issuedAt.getValue().toInstant().plus(AuthorizationCode.EXPIRES_MIN, ChronoUnit.MINUTES)))
            ),
            accessType,
            linkedAccessTokenCoreId,
            linkedIdTokenCoreId,
            linkedRefreshTokenCoreId
        );
    }
}
