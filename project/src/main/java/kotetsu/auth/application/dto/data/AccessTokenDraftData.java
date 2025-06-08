package kotetsu.auth.application.dto.data;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

public class AccessTokenDraftData {
    @Getter
    private final UUID code;

    @Getter
    private final String value;

    @Getter
    private final String issuer;

    @Getter
    private final String subject;

    @Getter
    private final List<ScopeData> scopes;

    @Getter
    private final List<String> audiences;

    private AccessTokenDraftData(
        final UUID code,
        final String value,
        final String issuer,
        final String subject,
        final List<ScopeData> scopes,
        final List<String> audiences
    ) {
        this.code = code;
        this.value = value;
        this.issuer = issuer;
        this.subject = subject;
        this.scopes = scopes;
        this.audiences = audiences;
    }

    public static AccessTokenDraftData of(
        final UUID code,
        final String value,
        final String issuer,
        final String subject,
        final List<ScopeData> scopes,
        final List<String> audiences
    ) {
        return new AccessTokenDraftData(
            code,
            value,
            issuer,
            subject,
            scopes,
            audiences
        );
    }
}
