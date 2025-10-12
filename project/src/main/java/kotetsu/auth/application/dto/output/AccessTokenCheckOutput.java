package kotetsu.auth.application.dto.output;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Value;

@Value
public class AccessTokenCheckOutput {
    @Getter
    private final boolean isActive;
    private final String scopeToken;
    private final String clientId;
    private final Long issuedAt;
    private final Long expiredAt;
    private final String subject;
    private final List<String> audiences;
    private final String issuer;
    private final String tokenType;

    public Optional<String> getScopeToken() {
        return Optional.ofNullable(scopeToken);
    }

    public Optional<String> getClientId() {
        return Optional.ofNullable(clientId);
    }

    public Optional<Long> getIssuedAt() {
        return Optional.ofNullable(issuedAt);
    }

    public Optional<Long> getExpiredAt() {
        return Optional.ofNullable(expiredAt);
    }

    public Optional<String> getSubject() {
        return Optional.ofNullable(subject);
    }

    public Optional<List<String>> getAudiences() {
        return Optional.ofNullable(audiences);
    }

    public Optional<String> getIssuer() {
        return Optional.ofNullable(issuer);
    }

    public Optional<String> getTokenType() {
        return Optional.ofNullable(tokenType);
    }

    private AccessTokenCheckOutput(
        final boolean isActive,
        final String scopeToken,
        final String clientId,
        final Long issuedAt,
        final Long expiredAt,
        final String subject,
        final List<String> audiences,
        final String issuer,
        final String tokenType
    ) {
        this.isActive = isActive;
        this.scopeToken = scopeToken;
        this.clientId = clientId;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.subject = subject;
        this.audiences = audiences;
        this.issuer = issuer;
        this.tokenType = tokenType;
    }

    public static AccessTokenCheckOutput of(
        final boolean isActive,
        final String scopeToken,
        final String clientId,
        final Long issuedAt,
        final Long expiredAt,
        final String subject,
        final List<String> audiences,
        final String issuer,
        final String tokenType
    ) {
        final AccessTokenCheckOutput output = new AccessTokenCheckOutput(
            isActive,
            scopeToken,
            clientId,
            issuedAt,
            expiredAt,
            subject,
            audiences,
            issuer,
            tokenType
        );

        return output;
    }
}
