package kotetsu.auth.application.usecase;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.dto.input.GetClientInformationInput;
import kotetsu.auth.application.dto.output.ClientInformationOutput;
import kotetsu.auth.application.query.IFindClientInformationPort;

@Component
public class GetClientInformationUsecase {
    private final IFindClientInformationPort findClientInformationPort;

    public GetClientInformationUsecase(final IFindClientInformationPort findClientInformationPort) {
        this.findClientInformationPort = findClientInformationPort;
    }
    
    public ClientInformationOutput execute(final GetClientInformationInput input) {
        final ClientInformationData clientInformationData = findClientInformationPort.findByClientId(input.getClientId())
            .orElseThrow();

        return ClientInformationOutput.of(
            clientInformationData.getClientId(),
            clientInformationData.getName()
        );
    }
}
