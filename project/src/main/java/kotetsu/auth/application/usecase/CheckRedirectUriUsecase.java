package kotetsu.auth.application.usecase;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ClientRedirectInformation;
import kotetsu.auth.application.domain.repository.IFetchClientRedirectInformationPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.dto.input.CheckRedirectUriInput;
import kotetsu.auth.application.dto.output.RedirectUriCheckOutput;
import kotetsu.auth.application.exception.InputNullRuntimeException;

public class CheckRedirectUriUsecase {
    private final IFetchClientRedirectInformationPort fetchClientRedirectInformationPort;

    public CheckRedirectUriUsecase(final IFetchClientRedirectInformationPort fetchClientRedirectInformationPort) {
        this.fetchClientRedirectInformationPort = fetchClientRedirectInformationPort;
    }

    public RedirectUriCheckOutput execute(CheckRedirectUriInput input) {
        if (input == null) {
            throw new InputNullRuntimeException();
        }

        Optional<ClientRedirectInformation> clientRedirectInfo = fetchClientRedirectInformationPort.fetch(ClientId.of(input.getClientId()));
        if (clientRedirectInfo.isEmpty()) {
            return RedirectUriCheckOutput.of(false);
        }

        if (!clientRedirectInfo.get().getRedirectUri().equals(ClientRedirectUri.of(input.getRedirectUri()))) {
            return RedirectUriCheckOutput.of(false);
        }

        return RedirectUriCheckOutput.of(true);
    }
}
