package kotetsu.auth.application.dto.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OidcPublicKeyJwk {
    private final String kid;
    private final String kty;
    private final String alg;
    private final String use;
    private final String n;
    private final String e;
}
