package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.UserProfile;
import kotetsu.auth.application.domain.exception.UserProfileNotFoundException;
import kotetsu.auth.application.domain.repository.IFetchUserProfileByEmailRepository;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.dto.GetUserProfileEmailInput;
import kotetsu.auth.application.dto.UserProfileOutput;

@Component
public class GetUserProfileByEmailUsecase {

    final IFetchUserProfileByEmailRepository fetchUserProfileByEmailRepository;

    public GetUserProfileByEmailUsecase(final IFetchUserProfileByEmailRepository fetchUserProfileByEmailRepository) {
        this.fetchUserProfileByEmailRepository = fetchUserProfileByEmailRepository;
    }

    public UserProfileOutput getUserProfile(GetUserProfileEmailInput input) throws UserProfileNotFoundException {
        UserProfile userProfile = fetchUserProfileByEmailRepository.fetchByEmail(Email.of(input.getEmail()));

        if (userProfile == null) {
            throw new UserProfileNotFoundException();
        }

        return UserProfileOutput.of(
            userProfile.getCode().getValue(),
            userProfile.getName().getValue(),
            userProfile.getEmail().getValue(),
            userProfile.getImageUrl().getValue()
        );
    }
}
