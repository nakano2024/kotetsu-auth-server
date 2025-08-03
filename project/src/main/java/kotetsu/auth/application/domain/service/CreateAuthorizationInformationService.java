package kotetsu.auth.application.domain.service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import kotetsu.auth.application.constant.AuthorizationCodeConstant;
import kotetsu.auth.application.domain.entity.AuthorizationInformation;
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

    public AuthorizationInformation create(
        final Id id,
        final AuthorizationCodeChallenge challenge,
        final AccessType accessType
    ) {
        final Date current = fetchCurrentInstantPort.fetch();

        return AuthorizationInformation.of(
            id,
            AuthorizationCode.of(
                AuthorizationCodeToken.of(generateRandomStringPort.generate(32)),
                challenge,
                ExpiredAt.of(Date.from(current.toInstant().plus(AuthorizationCodeConstant.EXPIRES_MIN, ChronoUnit.MINUTES)))
            ),
            accessType
        );
    }
}
