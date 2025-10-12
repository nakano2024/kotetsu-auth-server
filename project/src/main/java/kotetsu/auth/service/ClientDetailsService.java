package kotetsu.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kotetsu.auth.application.dto.input.ClientCredentialOutput;
import kotetsu.auth.application.dto.input.GetClientCredentialInput;
import kotetsu.auth.application.exception.ClientCredentialNotFoundException;
import kotetsu.auth.application.usecase.GetClientCredentialUsecase;
import kotetsu.auth.dto.security.ClientDetails;

@Service
public class ClientDetailsService implements UserDetailsService {
    private final GetClientCredentialUsecase usecase;

    public ClientDetailsService(final GetClientCredentialUsecase usecase) {
        this.usecase = usecase;
    }

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
        try {
            final ClientCredentialOutput output = usecase.execute(GetClientCredentialInput.of(clientId));

            return new ClientDetails(output.getClientId(), output.getClientSecret());
        }
        catch(ClientCredentialNotFoundException exception) {
            throw new UsernameNotFoundException("Client not found: " + clientId);
        }
    }
}
