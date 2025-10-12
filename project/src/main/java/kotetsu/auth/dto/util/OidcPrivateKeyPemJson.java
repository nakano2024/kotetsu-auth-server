package kotetsu.auth.dto.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class OidcPrivateKeyPemJson {
    private final String kid;
    private final String pem;
}
