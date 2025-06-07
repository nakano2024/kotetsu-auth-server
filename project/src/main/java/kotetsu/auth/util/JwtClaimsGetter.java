package kotetsu.auth.util;

import java.security.PublicKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import kotetsu.auth.exception.BearerTokenInvalidException;

@Component
public class JwtClaimsGetter {

    private final PublicKeyGetter publicKeyGetter;

    public JwtClaimsGetter(final PublicKeyGetter publicKeyGetter) {
        this.publicKeyGetter = publicKeyGetter;
    }

    public Claims getClaims(final String jwt) throws BearerTokenInvalidException {
        final PublicKey publicKey = publicKeyGetter.getPublicKey();

        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        }
        catch(JwtException e) {
            throw new BearerTokenInvalidException(e.getMessage());
        }
    }
}
