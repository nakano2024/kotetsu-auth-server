package kotetsu.auth.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import kotetsu.auth.application.dto.output.OidcPublicKeyCertsOutput;
import kotetsu.auth.application.dto.output.OidcPublicKeyJwkOutput;
import kotetsu.auth.application.dto.util.OidcPublicKeyJwk;
import kotetsu.auth.application.exception.OidcPublicKeyJwksNullRuntimeException;
import kotetsu.auth.application.util.IFetchOidcPublicKeyJwksPort;

public class GetOidcPublicKeyCertsUsecase {
    private IFetchOidcPublicKeyJwksPort fetchOidcPublicKeyJwksPort;

    public OidcPublicKeyCertsOutput execute() {
        final List<OidcPublicKeyJwk> jwks = fetchOidcPublicKeyJwksPort.fetch()
            .orElseThrow(() -> new OidcPublicKeyJwksNullRuntimeException());

        return OidcPublicKeyCertsOutput.of(jwks.stream()
            .map(jwk -> OidcPublicKeyJwkOutput.of(
                jwk.getKid(),
                jwk.getKty(),
                jwk.getAlg(),
                jwk.getUse(),
                jwk.getN(),
                jwk.getE()
            )).collect(Collectors.toList())
        );
    }
}
