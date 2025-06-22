package kotetsu.auth.application.dto.store;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

public class AccessTokenStore {
    @Getter
    private final String code;

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
        final String code,
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes,
        final Date issuedAt,
        final Date expiredAt
    ) {
        this.code = code;
        this.issuer = issuer;
        this.subject = subject;
        this.scopeCodes = scopeCodes;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static AccessTokenStore of(
        final String code,
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes,
        final Date issuedAt,
        final Date expiredAt
    ) {
        return new AccessTokenStore(
            code,
            issuer,
            subject,
            scopeCodes,
            issuedAt,
            expiredAt
        );
    }
}