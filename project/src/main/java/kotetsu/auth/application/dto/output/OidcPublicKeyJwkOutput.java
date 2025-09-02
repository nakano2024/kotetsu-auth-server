package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class OidcPublicKeyJwkOutput {
    @Getter
    private final String kid;

    @Getter
    private final String kty;

    @Getter
    private final String alg;

    @Getter
    private final String use;

    @Getter
    private final String n;

    @Getter
    private final String e;

    private OidcPublicKeyJwkOutput(
        final String kid,
        final String kty,
        final String alg,
        final String use,
        final String n,
        final String e
    ) {
        this.kid = kid;
        this.kty = kty;
        this.alg = alg;
        this.use = use;
        this.n = n;
        this.e = e;
    }

    public static OidcPublicKeyJwkOutput of(
        final String kid,
        final String kty,
        final String alg,
        final String use,
        final String n,
        final String e
    ) {
        final OidcPublicKeyJwkOutput output = new OidcPublicKeyJwkOutput(
            kid,
            kty,
            alg,
            use,
            n,
            e
        );

        return output;
    }
}
