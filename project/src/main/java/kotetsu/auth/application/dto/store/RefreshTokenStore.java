package kotetsu.auth.application.dto.store;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

public class RefreshTokenStore {
    @Getter
    private final String code;

    @Getter
    private final UUID accessTokenDraftCode;

    @Getter
    private final UUID idTokenDraftCode;

    @Getter
    private final Date issuedAt;

    @Getter
    private final Date expiredAt;

    private RefreshTokenStore(
        final String code,
        final UUID accessTokenDraftCode,
        final UUID idTokenDraftCode,
        final Date issuedAt,
        final Date expiredAt
    ) {
        this.code = code;
        this.accessTokenDraftCode = accessTokenDraftCode;
        this.idTokenDraftCode = idTokenDraftCode;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static RefreshTokenStore of(
        final String code,
        final UUID accessTokenDraftCode,
        final UUID idTokenDraftCode,
        final Date issuedAt,
        final Date expiredAt
    ) {
        return new RefreshTokenStore(
            code,
            accessTokenDraftCode,
            idTokenDraftCode,
            issuedAt,
            expiredAt
        );
    }
}