package kotetsu.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostOAuth2TokenResponse {
    @JsonProperty("token")
    private final String accessToken;

    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("expires_in")
    private final Long expiresIn;

    @JsonProperty("scope")
    private final String scope;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonProperty("id_token")
    private final String idToken;
}
