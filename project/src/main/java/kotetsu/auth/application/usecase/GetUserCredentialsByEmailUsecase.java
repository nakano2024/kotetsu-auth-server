package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.application.dto.input.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.output.UserCredentialsOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.query.IFindUserCredentialByEmailPort;

@Component
public class GetUserCredentialsByEmailUsecase {
    private final IFindUserCredentialByEmailPort findUserCredentialByEmailPort;

    public GetUserCredentialsByEmailUsecase(final IFindUserCredentialByEmailPort findUserCredentialByEmailPort) {
        this.findUserCredentialByEmailPort = findUserCredentialByEmailPort;
    }

    @Transactional
    public UserCredentialsOutput execute(GetUserCredentialEmailInput input) throws UserCredentialNotFoundException {
        if (input == null) {
            throw new InputNullRuntimeException();
        }        
        
        UserCredentialData userCredential = findUserCredentialByEmailPort.findByEmail(input.getEmail())
            .orElseThrow(() -> new UserCredentialNotFoundException());

        return UserCredentialsOutput.of(
            userCredential.getKey(),
            userCredential.getName(),
            userCredential.getImageUrl(),
            userCredential.getEmail(),
            userCredential.getHashedPassword(),
            userCredential.isActive()
        );
    }
}
