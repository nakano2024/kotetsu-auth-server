package kotetsu.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import kotetsu.auth.dto.resource.IdTokenResource;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"status", "token"})
public class PasswordAuthResponse {
    @JsonProperty("status")
    private final String status ;

    @JsonProperty("token")
    private final IdTokenResource idToken;

    public PasswordAuthResponse(final IdTokenResource idToken) {
        status = "success";
        this.idToken = idToken;
    }
}
