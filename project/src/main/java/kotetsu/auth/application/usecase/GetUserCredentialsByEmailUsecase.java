package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.application.dto.input.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.output.UserCredentialsOutput;
import kotetsu.auth.application.persistence.IFindUserCredentialByEmailPort;

@Component
public class GetUserCredentialsByEmailUsecase {
    private final IFindUserCredentialByEmailPort findUserCredentialByEmailPort;

    public GetUserCredentialsByEmailUsecase(final IFindUserCredentialByEmailPort findUserCredentialByEmailPort) {
        this.findUserCredentialByEmailPort = findUserCredentialByEmailPort;
    }

    public UserCredentialsOutput getUserCredentials(GetUserCredentialEmailInput input) throws UserCredentialNotFoundException {
        UserCredentialData userCredential = findUserCredentialByEmailPort.findByEmail(input.getEmail());

        if (userCredential == null) {
            throw new UserCredentialNotFoundException();
        }

        return UserCredentialsOutput.of(
            userCredential.getEmail(),
            userCredential.getHashedPassword()
        );
    }
}
