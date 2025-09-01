package kotetsu.auth.dto.util;

import java.security.PrivateKey;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OidcPrivateKey {
    @Getter
    private final String kid;

    @Getter
    private final PrivateKey key;
}
