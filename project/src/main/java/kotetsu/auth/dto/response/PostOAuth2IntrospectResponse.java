package kotetsu.auth.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostOAuth2IntrospectResponse {
    @JsonProperty("active")
    private final boolean active;

    @JsonProperty("scope")
    private final String scope;

    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("iat")
    private final Long iat;

    @JsonProperty("exp")
    private final Long exp;

    @JsonProperty("sub")
    private final String sub;

    @JsonProperty("aud")
    private final List<String> audiences;

    @JsonProperty("iss")
    private final String issuer;

    @JsonProperty("token_type")
    private final String tokenType;
}
