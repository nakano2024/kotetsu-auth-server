package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.domain.entity.AccessTokenBody;
import kotetsu.auth.application.domain.entity.Authorization;
import kotetsu.auth.application.domain.entity.IdTokenBody;
import kotetsu.auth.application.domain.entity.RefreshTokenBody;
import kotetsu.auth.application.domain.util.IFetchCurrentDatePort;
import kotetsu.auth.application.domain.util.IGenerateRandomStringPort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeToken;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.Id;

public class CreateAuthorizationInformationService {

    private final IGenerateRandomStringPort generateRandomStringPort;
    final IFetchCurrentDatePort fetchCurrentInstantPort;

    public CreateAuthorizationInformationService(
        final IGenerateRandomStringPort generateRandomStringPort,
        final IFetchCurrentDatePort fetchCurrentInstantPort
    ) {
        this.generateRandomStringPort = generateRandomStringPort;
        this.fetchCurrentInstantPort = fetchCurrentInstantPort;
    }

    public Authorization create(
        final Id id,
        final AuthorizationCodeChallenge challenge,
        final AccessType accessType,
        final AccessTokenBody accessTokenBody,
        final IdTokenBody idTokenBody,
        final RefreshTokenBody refreshTokenBody
    ) {
        final Date currentDate = fetchCurrentInstantPort.fetch();

        return Authorization.of(
            id,
            AuthorizationCode.of(
                AuthorizationCodeToken.of(generateRandomStringPort.generate(AuthorizationCodeToken.LENGTH)),
                challenge,
                ExpiredAt.of(Date.from(currentDate.toInstant().plus(AuthorizationCode.EXPIRES_MIN, ChronoUnit.MINUTES)))
            ),
            accessType,
            accessTokenBody,
            idTokenBody,
            refreshTokenBody
        );
    }
}
