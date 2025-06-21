package kotetsu.auth.application.dto.store;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

public class AccessTokenStore {
    @Getter
    private final String value;

    @Getter
    private final String issuer;

    @Getter
    private final UUID subject;

    @Getter
    private final List<UUID> scopeCodes;

    @Getter
    private final Date issuedAt;

    @Getter
    private final Date expiredAt;

    private AccessTokenStore(
        final String value,
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes,
        final Date issuedAt,
        final Date expiredAt
    ) {
        this.value = value;
        this.issuer = issuer;
        this.subject = subject;
        this.scopeCodes = scopeCodes;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static AccessTokenStore of(
        final String value,
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes,
        final Date issuedAt,
        final Date expiredAt
    ) {
        return new AccessTokenStore(
            value,
            issuer,
            subject,
            scopeCodes,
            issuedAt,
            expiredAt
        );
    }
}