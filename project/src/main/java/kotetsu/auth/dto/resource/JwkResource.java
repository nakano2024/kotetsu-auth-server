package kotetsu.auth.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwkResource {
    @JsonProperty("kid")
    final String kid;

    @JsonProperty("kty")
    final String kty;

    @JsonProperty("alg")
    final String alg;

    @JsonProperty("use")
    final String use;

    @JsonProperty("n")
    final String n;

    @JsonProperty("e")
    final String e;
}
