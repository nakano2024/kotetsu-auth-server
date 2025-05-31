package kotetsu.auth.util;

import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class InternalAuthIdTokenGenerator {
    private final PrivateKeyGetter privateKeyGetter;

    public InternalAuthIdTokenGenerator(PrivateKeyGetter privateKeyGetter) {
        this.privateKeyGetter = privateKeyGetter;
    }

    public String generate(
        final String subject,
        final Map<String, String> profile
    ) {
        Instant now = Instant.now();

        PrivateKey privateKey = privateKeyGetter.getPrivateKey();

        return Jwts.builder()
            .subject(subject)
            .claim("profile", profile)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
            .signWith(privateKey, Jwts.SIG.RS256)
            .compact();
    }
}
