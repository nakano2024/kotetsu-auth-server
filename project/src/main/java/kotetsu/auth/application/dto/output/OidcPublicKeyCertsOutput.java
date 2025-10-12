package kotetsu.auth.application.dto.output;

import java.util.List;

import lombok.Getter;

public class OidcPublicKeyCertsOutput {
    @Getter
    private final List<OidcPublicKeyJwkOutput> keys;

    private OidcPublicKeyCertsOutput(final List<OidcPublicKeyJwkOutput> keys) {
        this.keys = keys;
    }

    public static OidcPublicKeyCertsOutput of(final List<OidcPublicKeyJwkOutput> keys) {
        final OidcPublicKeyCertsOutput output = new OidcPublicKeyCertsOutput(keys);

        return output;
    }
}
