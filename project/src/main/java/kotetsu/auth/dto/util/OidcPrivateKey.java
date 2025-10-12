package kotetsu.auth.dto.util;

import java.security.PrivateKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OidcPrivateKey {
    @Getter
    private final String kid;

    @Getter
    private final PrivateKey key;
}
