package kotetsu.auth.application.domain.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import kotetsu.auth.application.domain.value.Audience;
import kotetsu.auth.application.domain.value.Code;
import kotetsu.auth.application.domain.value.Issuer;
import lombok.Getter;

public class AccessTokenDraft {
    @Getter
    private final Code code;

    @Getter
    private final Issuer issuer;

    @Getter
    private final List<Audience> audiences;

    @Getter
    private final Subject subject;

    @Getter
    private final List<Scope> scopes;

    private AccessTokenDraft(
        final Code code,
        final Issuer issuer,
        final List<Audience> audiences,
        final Subject subject,
        final List<Scope> scopes
    ) {
        this.code = code;
        this.issuer = issuer;
        this.audiences = audiences;
        this.subject = subject;
        this.scopes = scopes;
    }

    public static AccessTokenDraft of(
        final Code code,
        final Issuer issuer,
        final List<Audience> audiences,
        final Subject subject,
        final List<Scope> scopes        
    ) {
        final List<Audience> uniqueAudiences = removeAudienceDuplicates(audiences);

        final AccessTokenDraft accessTokenDraft = new AccessTokenDraft(
            code,
            issuer,
            uniqueAudiences,
            subject,
            scopes
        );
        return accessTokenDraft;
    }

    private static List<Audience> removeAudienceDuplicates(final List<Audience> audiences) {
        final Set<String> seen = new HashSet<>();

        return audiences.stream()
            .filter(audience -> seen.add(audience.getValue()))
            .collect(Collectors.toList());
    }
}
