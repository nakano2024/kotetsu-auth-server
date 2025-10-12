package kotetsu.auth.dto.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OidcPublicKeyJwkWrapperJson {
    private final List<OidcPublicKeyJwkJson> keys;
}
