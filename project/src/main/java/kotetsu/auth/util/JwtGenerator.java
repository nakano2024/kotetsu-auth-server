package kotetsu.auth.util;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import kotetsu.auth.application.util.IGenerateIInternalAuthTokenPort;

@Component
public class JwtGenerator implements IGenerateIInternalAuthTokenPort {
    private final PrivateKeyGetter privateKeyGetter;

    public JwtGenerator(PrivateKeyGetter privateKeyGetter) {
        this.privateKeyGetter = privateKeyGetter;
    }

    @Override
    public String generate(
        final String subject,
        final Date issuedAt,
        final Date expiredAt,
        final Map<String, String> profile
    ) {
        PrivateKey privateKey = privateKeyGetter.getPrivateKey();

        return Jwts.builder()
            .subject(subject)
            .claim("profile", profile)
            .issuedAt(issuedAt)
            .expiration(expiredAt)
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact();
    }
}
