package kotetsu.auth.application.dto.store;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

public class AuthorizationCodeStore {
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

    @Getter
    private final boolean enableOpenid;

    @Getter
    private final boolean enableOfflineAccess;

    private AuthorizationCodeStore(
        String value,
        String challenge,
        UUID accessTokenDraftCode,
        UUID idTokenDraftCode,
        Date issuedAt,
        Date expiredAt,
        boolean enableOpenid,
        boolean enableOfflineAccess
    ) {
        this.value = value;
        this.challenge = challenge;
        this.accessTokenDraftCode = accessTokenDraftCode;
        this.idTokenDraftCode = idTokenDraftCode;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.enableOpenid = enableOpenid;
        this.enableOfflineAccess = enableOfflineAccess;
    }

    public static AuthorizationCodeStore of(
        final String value,
        final String challenge,
        final UUID accessTokenDraftCode,
        final UUID idTokenDraftCode,
        final Date issuedAt,
        final Date expiredAt,
        final boolean enableOpenid,
        final boolean enableOfflineAccess
    ) {
        return new AuthorizationCodeStore(
            value,
            challenge,
            accessTokenDraftCode,
            idTokenDraftCode,
            issuedAt,
            expiredAt,
            enableOpenid,
            enableOfflineAccess
        );
    }
}
