package kotetsu.auth.application.dto.data;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

public class AccessTokenDraftData {
    @Getter
    private final UUID code;

    @Getter
    private final String issuer;

    @Getter
    private final UUID subject;

    @Getter
    private final List<ScopeData> scopes;

    @Getter
    private final List<String> audiences;

    private AccessTokenDraftData(
        final UUID code,
        final String issuer,
        final UUID subject,
        final List<ScopeData> scopes,
        final List<String> audiences
    ) {
        this.code = code;
        this.issuer = issuer;
        this.subject = subject;
        this.scopes = scopes;
        this.audiences = audiences;
    }

    public static AccessTokenDraftData of(
        final UUID code,
        final String issuer,
        final UUID subject,
        final List<ScopeData> scopes,
        final List<String> audiences
    ) {
        return new AccessTokenDraftData(
            code,
            issuer,
            subject,
            scopes,
            audiences
        );
    }
}
