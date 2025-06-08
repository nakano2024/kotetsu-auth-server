package kotetsu.auth.application.dto.store;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

public class AccessTokenDraftStore {
    @Getter
    private final String value;

    @Getter
    private final String issuer;

    @Getter
    private final UUID subject;

    @Getter
    private final List<UUID> scopeCodes;

    private AccessTokenDraftStore(
        final String value,
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes
    ) {
        this.value = value;
        this.issuer = issuer;
        this.subject = subject;
        this.scopeCodes = scopeCodes;
    }

    public static AccessTokenDraftStore of(
        final String value,
        final String issuer,
        final UUID subject,
        final List<UUID> scopeCodes
    ) {
        return new AccessTokenDraftStore(
            value,
            issuer,
            subject,
            scopeCodes
        );
    }
}
