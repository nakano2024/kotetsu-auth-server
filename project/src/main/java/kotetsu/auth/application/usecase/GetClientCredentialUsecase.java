package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ClientCredentialData;
import kotetsu.auth.application.dto.input.ClientCredentialOutput;
import kotetsu.auth.application.dto.output.GetClientCredentialInput;
import kotetsu.auth.application.exception.ClientCredentialNotFoundException;
import kotetsu.auth.application.exception.InputNullRuntimeException;
import kotetsu.auth.application.query.IFindClientCredentialPort;

@Component
public class GetClientCredentialUsecase {
    private final IFindClientCredentialPort findClientCredentialPort;

    public GetClientCredentialUsecase(final IFindClientCredentialPort findClientCredentialPort) {
        this.findClientCredentialPort = findClientCredentialPort;
    }

    public ClientCredentialOutput execute(GetClientCredentialInput input)
        throws ClientCredentialNotFoundException
    {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        final ClientCredentialData clientCredential = findClientCredentialPort.findByClientId(input.getClientId())
            .orElseThrow(() -> new ClientCredentialNotFoundException());

        return ClientCredentialOutput.of(clientCredential.getClientId(), clientCredential.getHashedClientSecret());
    }
}
