package kotetsu.auth.dto.requestparam;

import java.beans.ConstructorProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GetOAuth2AuthorizationRequestParam {
    @NotBlank
    private final String clientId;

    @NotBlank
    private final String redirectUri;

    @NotBlank
    private final String nonce;

    @NotBlank
    private final String state;

    @NotBlank
    private final String codeChallenge;

    @NotBlank
    private final String scope;

    @NotBlank
    @Pattern(regexp = "^(offline|online)$")
    private final String accessType;
    
    @ConstructorProperties({
        "client_id",
        "redirect_uri",
        "nonce",
        "state",
        "code_challenge",
        "scope",
        "access_type"
    })
    public GetOAuth2AuthorizationRequestParam(
        final String clientId,
        final String redirectUri,
        final String nonce,
        final String state,
        final String codeChallenge,
        final String scope,
        final String accessType
    ) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.nonce = nonce;
        this.state = state;
        this.codeChallenge = codeChallenge;
        this.scope = scope;
        this.accessType = accessType;
    }
}
