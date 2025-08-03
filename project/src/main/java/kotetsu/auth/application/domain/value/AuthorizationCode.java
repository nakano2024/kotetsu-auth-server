package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class AuthorizationCode {
    public static final int EXPIRES_MIN = 1;

    @Getter
    private final AuthorizationCodeToken token;

    @Getter
    private final AuthorizationCodeChallenge challenge;

    @Getter
    private final ExpiredAt expiredAt;

    private AuthorizationCode(final AuthorizationCodeToken token, final AuthorizationCodeChallenge challenge, final ExpiredAt expiredAt) {
        this.token = token;
        this.challenge = challenge;
        this.expiredAt = expiredAt;
    }

    public static AuthorizationCode of(final AuthorizationCodeToken token, final AuthorizationCodeChallenge challenge, final ExpiredAt expiredAt) {
        final AuthorizationCode authorizationCode = new AuthorizationCode(token, challenge, expiredAt);
        return authorizationCode;
    }
}
