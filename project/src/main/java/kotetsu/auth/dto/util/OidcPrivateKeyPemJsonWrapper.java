package kotetsu.auth.dto.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class OidcPrivateKeyPemJsonWrapper {
    private final List<OidcPrivateKeyPemJson> keys;
}
