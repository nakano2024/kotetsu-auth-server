package kotetsu.auth.application.dto.output;

import java.util.List;
import lombok.Getter;

public class TokenOutput {
    @Getter
    private final String accessToken;

    @Getter
    private final String tokenType;

    @Getter
    private final Long expiresIn;

    @Getter
    private final String refreshToken;

    @Getter
    private final String idToken;

    @Getter
    private final List<String> scopes;

    @Getter
    private final List<String> audiences;

    private TokenOutput(
        final String accessToken,
        final String tokenType,
        final Long expiresIn,
        final String refreshToken,
        final String idToken,
        final List<String> scopes,
        final List<String> audiences
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
        this.scopes = scopes;
        this.audiences = audiences;
    }

    public static TokenOutput of(
        final String accessToken,
        final String tokenType,
        final Long expiresIn,
        final String refreshToken,
        final String idToken,
        final List<String> scopes,
        final List<String> audiences
    ) {
        return new TokenOutput(accessToken, tokenType, expiresIn, refreshToken, idToken, scopes, audiences);
    }
}