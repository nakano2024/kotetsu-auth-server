package kotetsu.auth.application.util;

import java.util.List;
import java.util.Optional;

import kotetsu.auth.application.dto.util.OidcPublicKeyJwk;

public interface IFetchOidcPublicKeyJwksPort {
    Optional<List<OidcPublicKeyJwk>> fetch();
}
