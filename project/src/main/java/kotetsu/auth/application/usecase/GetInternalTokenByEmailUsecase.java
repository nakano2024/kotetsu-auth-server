package kotetsu.auth.application.usecase;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.UserProfileData;
import kotetsu.auth.application.dto.input.GetInternalTokenInput;
import kotetsu.auth.application.dto.output.IdTokenOutput;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.exception.UserProfileNotFoundIOException;
import kotetsu.auth.application.persistence.IFindUserProfileByEmailPort;
import kotetsu.auth.application.util.IGenerateIInternalAuthTokenPort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;

@Component
public class GetInternalTokenByEmailUsecase {

    final IFindUserProfileByEmailPort findUserProfileByEmailPort;
    final IGenerateIInternalAuthTokenPort generateInternalTokenPort;
    final IGetCurrentInstantPort getCurrentInstantPort;

    public GetInternalTokenByEmailUsecase(
        final IFindUserProfileByEmailPort findUserProfileByEmailPort,
        final IGenerateIInternalAuthTokenPort generateInternalTokenPort,
        final IGetCurrentInstantPort getCurrentInstantPort
    ) {
        this.findUserProfileByEmailPort = findUserProfileByEmailPort;
        this.generateInternalTokenPort = generateInternalTokenPort;
        this.getCurrentInstantPort = getCurrentInstantPort;
    }

    public IdTokenOutput getInternalToken(GetInternalTokenInput input) throws UserProfileNotFoundIOException {
        if (input == null) {
            throw new InputNullException();
        }

        final UserProfileData userProfile = findUserProfileByEmailPort.findByEmail(input.getEmail());

        if (userProfile == null) {
            throw new UserProfileNotFoundIOException();
        }

        Instant current = getCurrentInstantPort.getCurrent();
        Date issuedAt = Date.from(current);
        Date expiresAt = Date.from(current.plus(1, ChronoUnit.DAYS));
        String idToken = generateInternalTokenPort.generate(
            userProfile.getCode().toString(),
            issuedAt,
            expiresAt,
            Map.of(
                "name", userProfile.getName(),
                "email", userProfile.getEmail(),
                "imageUrl", userProfile.getImageUrl()
            )
        );

        return IdTokenOutput.of(
            idToken,
            "Bearer",
            (Long) (Math.abs(expiresAt.getTime() - issuedAt.getTime()) / 1000)
        );
    }
}
