package kotetsu.auth.application.dto.input;
import lombok.Getter;

public class GetClientInformationInput {
    @Getter
    private final String clientId;

    public GetClientInformationInput(final String clientId) {
        this.clientId = clientId;
    }

    public static GetClientInformationInput of(final String clientId) {
        final GetClientInformationInput output = new GetClientInformationInput(clientId);

        return output;
    }
}
