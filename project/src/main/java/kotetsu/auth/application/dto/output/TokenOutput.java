package kotetsu.auth.application.dto.output;

import java.util.Optional;

import lombok.Getter;

public class TokenOutput {
    @Getter
    private final String accessToken;

    @Getter
    private final String tokenType;

    @Getter
    private final Long expiresIn;

    private final String refreshToken;

    private final String idToken;

    @Getter
    private final String scopeToken;

    public Optional<String> getRefreshToken() {
        return Optional.ofNullable(refreshToken);
    }

    public Optional<String> getIdToken() {
        return Optional.ofNullable(idToken);
    }

    private TokenOutput(
        final String accessToken,
        final String tokenType,
        final Long issuedAt,
        final Long expiresIn,
        final String refreshToken,
        final String idToken,
        final String scopeToken
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
        this.scopeToken = scopeToken;
    }

    public static TokenOutput of(
        final String accessToken,
        final String tokenType,
        final Long issuedAt,
        final Long expiresIn,
        final String refreshToken,
        final String idToken,
        final String scopeToken
    ) {
        return new TokenOutput(
            accessToken,
            tokenType,
            issuedAt,
            expiresIn,
            refreshToken,
            idToken,
            scopeToken
        );
    }
}