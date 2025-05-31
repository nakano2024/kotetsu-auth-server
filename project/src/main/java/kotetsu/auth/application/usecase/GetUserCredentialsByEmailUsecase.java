package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.UserCredential;
import kotetsu.auth.application.domain.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.domain.repository.IFetchUserCredentialByEmailRepository;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.dto.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.UserCredentialsOutput;

@Component
public class GetUserCredentialsByEmailUsecase {
    private final IFetchUserCredentialByEmailRepository fetchUserCredentialByEmailRepository;

    public GetUserCredentialsByEmailUsecase(IFetchUserCredentialByEmailRepository fetchUserCredentialByEmailRepository) {
        this.fetchUserCredentialByEmailRepository = fetchUserCredentialByEmailRepository;
    }

    public UserCredentialsOutput getUserCredentials(GetUserCredentialEmailInput input) throws UserCredentialNotFoundException {
        UserCredential user = fetchUserCredentialByEmailRepository.fetchByEmail(Email.of(input.getEmail()));

        if (user == null) {
            throw new UserCredentialNotFoundException();
        }

        return UserCredentialsOutput.of(
            user.getCode().getValue(),
            user.getEmail().getValue(),
            user.getPassword().getValue()
        );
    }
}
