package kotetsu.auth.dto.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OidcPublicKeyJwkWrapperJson {
    private final List<OidcPublicKeyJwkJson> keys;
}
