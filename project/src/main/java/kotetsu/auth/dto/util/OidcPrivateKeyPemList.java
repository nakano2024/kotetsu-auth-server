package kotetsu.auth.dto.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OidcPrivateKeyPemList {
    private final List<OidcPrivateKeyPem> keys;
}
