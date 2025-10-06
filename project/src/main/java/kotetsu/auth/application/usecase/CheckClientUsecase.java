package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.dto.input.CheckClientInput;
import kotetsu.auth.application.dto.output.ClientCheckOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;

@Component
public class CheckClientUsecase {
    private final IFetchRequesterClientPort fetchRequeterClientPort;

    public CheckClientUsecase(
        final IFetchRequesterClientPort fetchRequeterClientPort
    ) {
        this.fetchRequeterClientPort = fetchRequeterClientPort;
    }

    public ClientCheckOutput execute(final CheckClientInput input) {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        final RequesterClient requesterClient = fetchRequeterClientPort.fetch(ClientId.of(input.getClientId()))
            .orElse(null);
        if (requesterClient == null) {
            return ClientCheckOutput.of(ClientCheckOutput.STATUS_CLIENT_NOT_FOUND);
        }

        if(!requesterClient.getRedirectUri().equals(ClientRedirectUri.of(input.getRedirectUri()))) {
            return ClientCheckOutput.of(ClientCheckOutput.STATUS_INVALID_REDIRECT_URI);
        }

        return ClientCheckOutput.of(ClientCheckOutput.STATUS_OK);
    }
}
