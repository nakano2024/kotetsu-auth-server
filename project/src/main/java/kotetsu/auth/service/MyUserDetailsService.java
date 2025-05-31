package kotetsu.auth.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kotetsu.auth.application.domain.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.dto.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.UserCredentialsOutput;
import kotetsu.auth.application.usecase.GetUserCredentialsByEmailUsecase;

@Service
public class MyUserDetailsService implements UserDetailsService {

    final private GetUserCredentialsByEmailUsecase getUserCredentialsByEmailUsecase;

    public MyUserDetailsService(GetUserCredentialsByEmailUsecase getUserCredentialsByEmailUsecase) {
        this.getUserCredentialsByEmailUsecase = getUserCredentialsByEmailUsecase;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            UserCredentialsOutput output = getUserCredentialsByEmailUsecase.getUserCredentials(GetUserCredentialEmailInput.of(email));

            return new User(
                output.getEmail(),
                output.getHashedPassword(),
                Collections.emptyList()
            );
        }
        catch (UserCredentialNotFoundException exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }
}
