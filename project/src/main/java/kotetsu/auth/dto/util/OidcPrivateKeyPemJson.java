package kotetsu.auth.dto.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OidcPrivateKeyPemJson {
    private final String kid;
    private final String pem;
}
