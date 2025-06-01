package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.UserProfileData;
import kotetsu.auth.application.dto.input.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.output.UserProfileOutput;
import kotetsu.auth.application.exception.UserProfileNotFoundException;
import kotetsu.auth.application.persistence.IFindUserProfileByEmailPort;

@Component
public class GetUserProfileByEmailUsecase {

    final IFindUserProfileByEmailPort findUserProfileByEmailPort;

    public GetUserProfileByEmailUsecase(final IFindUserProfileByEmailPort findUserProfileByEmailPort) {
        this.findUserProfileByEmailPort = findUserProfileByEmailPort;
    }

    public UserProfileOutput getUserProfile(GetUserProfileEmailInput input) throws UserProfileNotFoundException {
        UserProfileData userProfile = findUserProfileByEmailPort.findByEmail(input.getEmail());

        if (userProfile == null) {
            throw new UserProfileNotFoundException();
        }

        return UserProfileOutput.of(
            userProfile.getCode(),
            userProfile.getName(),
            userProfile.getEmail(),
            userProfile.getImageUrl()
        );
    }
}
