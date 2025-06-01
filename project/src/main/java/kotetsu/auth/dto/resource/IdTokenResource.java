package kotetsu.auth.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdTokenResource {
    @JsonProperty("id_token")
    private final String idToken;

    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("expires_in")
    private final Long expiredIn;
}
