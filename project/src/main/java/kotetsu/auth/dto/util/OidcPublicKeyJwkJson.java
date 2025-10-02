package kotetsu.auth.dto.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OidcPublicKeyJwkJson {
    private final String kid;
    private final String kty;
    private final String alg;
    private final String use;
    private final String sig;
    private final String n;
    private final String e;
}
