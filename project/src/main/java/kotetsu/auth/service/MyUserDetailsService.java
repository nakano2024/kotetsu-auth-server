package kotetsu.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kotetsu.auth.application.dto.input.GetUserCredentialEmailInput;
import kotetsu.auth.application.dto.output.UserCredentialsOutput;
import kotetsu.auth.application.exception.UserCredentialNotFoundException;
import kotetsu.auth.application.usecase.GetUserCredentialsByEmailUsecase;
import kotetsu.auth.dto.security.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final GetUserCredentialsByEmailUsecase getUserCredentialsByEmailUsecase;

    public MyUserDetailsService(final GetUserCredentialsByEmailUsecase getUserCredentialsByEmailUsecase) {
        this.getUserCredentialsByEmailUsecase = getUserCredentialsByEmailUsecase;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            final UserCredentialsOutput output = getUserCredentialsByEmailUsecase.execute(GetUserCredentialEmailInput.of(email));
            return new MyUserDetails(
                output.getKey(),
                output.getName(),
                output.getImageUrl(),
                output.getEmail(),
                output.getHashedPassword(),
                output.isActive()
            );
        }
        catch(UserCredentialNotFoundException exception) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
    }
}