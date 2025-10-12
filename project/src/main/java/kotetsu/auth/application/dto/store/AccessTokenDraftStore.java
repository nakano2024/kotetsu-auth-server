package kotetsu.auth.application.dto.store;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

public class AccessTokenDraftStore {
    @Getter
    private final String issuer;

    @Getter
    private final UUID subject;

    @Getter
    private final List<UUID> scopeCodes;

    private AccessTokenDraftStore(
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes
    ) {
        this.issuer = issuer;
        this.subject = subject;
        this.scopeCodes = scopeCodes;
    }

    public static AccessTokenDraftStore of(
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes
    ) {
        return new AccessTokenDraftStore(
            issuer,
            subject,
            scopeCodes
        );
    }
}
