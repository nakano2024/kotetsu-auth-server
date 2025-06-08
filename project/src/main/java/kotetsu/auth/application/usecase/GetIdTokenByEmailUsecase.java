package kotetsu.auth.application.usecase;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.UserProfileData;
import kotetsu.auth.application.dto.input.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.output.IdTokenOutput;
import kotetsu.auth.application.exception.InputNullException;
import kotetsu.auth.application.exception.UserProfileNotFoundIOException;
import kotetsu.auth.application.persistence.IFindUserProfileByEmailPort;
import kotetsu.auth.application.util.IGenerateIdTokenPort;
import kotetsu.auth.application.util.IGetCurrentInstantPort;

@Component
public class GetIdTokenByEmailUsecase {

    final IFindUserProfileByEmailPort findUserProfileByEmailPort;
    final IGenerateIdTokenPort generateIdTokenPort;
    final IGetCurrentInstantPort getCurrentInstantPort;

    public GetIdTokenByEmailUsecase(
        final IFindUserProfileByEmailPort findUserProfileByEmailPort,
        final IGenerateIdTokenPort generateIdTokenPort,
        final IGetCurrentInstantPort getCurrentInstantPort
    ) {
        this.findUserProfileByEmailPort = findUserProfileByEmailPort;
        this.generateIdTokenPort = generateIdTokenPort;
        this.getCurrentInstantPort = getCurrentInstantPort;
    }

    public IdTokenOutput getUserProfile(GetUserProfileEmailInput input) throws UserProfileNotFoundIOException {
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
        String idToken = generateIdTokenPort.generate(
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
