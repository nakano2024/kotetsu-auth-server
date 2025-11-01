package kotetsu.auth.dto.requestparam;

import java.beans.ConstructorProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PostOAuth2AuthorizationDenyRequesParam {
    @NotBlank
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}.kotetsu.com$")
    private final String clientId;

    @NotBlank
    private final String redirectUri;

    @NotBlank
    private final String state;

    @ConstructorProperties({
        "client_id",
        "redirect_uri",
        "state"
    })
    public PostOAuth2AuthorizationDenyRequesParam(
        final String clientId,
        final String redirectUri,
        final String state
    ) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.state = state;
    }
}
