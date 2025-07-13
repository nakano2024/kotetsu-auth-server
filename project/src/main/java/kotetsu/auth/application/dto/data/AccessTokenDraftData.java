package kotetsu.auth.application.dto.data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    private final List<ResourceServerData> audiences;

    private AccessTokenDraftData(
        final UUID code,
        final String issuer,
        final UUID subject,
        final List<ScopeData> scopes,
        final List<ResourceServerData> audiences
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
        final List<ResourceServerData> resourceServers
    ) {
        return new AccessTokenDraftData(
            code,
            issuer,
            subject,
            scopes,
            new ArrayList<>(new LinkedHashSet<>(resourceServers)) // 重複を取りのぞくためにLinkedHashSetする
        );
    }
}
