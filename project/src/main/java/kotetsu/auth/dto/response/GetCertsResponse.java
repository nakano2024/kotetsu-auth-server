package kotetsu.auth.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import kotetsu.auth.dto.resource.JwkResource;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCertsResponse {
    @JsonProperty("keys")
    private List<JwkResource> keys;
}
