package kotetsu.auth.application.dto.store;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

public class RefreshTokenStore {
    @Getter
    private final String value;

    @Getter
    private final UUID accessTokenDraftId;

    @Getter
    private final UUID idTokenDraftId;

    @Getter
    private final Date issuedAt;

    @Getter
    private final Date expiredAt;

    private RefreshTokenStore(
        final String value,
        final UUID accessTokenDraftId,
        final UUID idTokenDraftId,
        final Date issuedAt,
        final Date expiredAt
    ) {
        this.value = value;
        this.accessTokenDraftId = accessTokenDraftId;
        this.idTokenDraftId = idTokenDraftId;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static RefreshTokenStore of(
        final String value,
        final UUID accessTokenDraftId,
        final UUID idTokenDraftId,
        final Date issuedAt,
        final Date expiredAt
    ) {
        return new RefreshTokenStore(
            value,
            accessTokenDraftId,
            idTokenDraftId,
            issuedAt,
            expiredAt
        );
    }
}