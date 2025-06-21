package kotetsu.auth.application.dto.data;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

public class AuthorizationCodeData {
    @Getter
    private final String value;

    @Getter
    private final String challenge;

    @Getter
    private final UUID accessTokenDraftCode;

    @Getter
    private final UUID idTokenDraftCode;

    @Getter
    private final Date issuedAt;

    @Getter
    private final Date expiredAt;

    private AuthorizationCodeData(
        final String value,
        final String challenge,
        final UUID accessTokenDraftCode,
        final UUID idTokenDraftCode,
        final Date issuedAt,
        final Date expiredAt
    ) {
        this.value = value;
        this.challenge = challenge;
        this.accessTokenDraftCode = accessTokenDraftCode;
        this.idTokenDraftCode = idTokenDraftCode;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static AuthorizationCodeData of(
        final String value,
        final String challenge,
        final UUID accessTokenDraftCode,
        final UUID idTokenDraftCode,
        final Date issuedAt,
        final Date expiredAt
    ) {
        return new AuthorizationCodeData(
            value,
            challenge,
            accessTokenDraftCode,
            idTokenDraftCode,
            issuedAt,
            expiredAt
        );
    }
}
