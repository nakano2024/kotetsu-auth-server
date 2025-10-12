package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class AuthorizationCode {
    public static final int EXPIRES_MIN = 1;

    @Getter
    private final AuthorizationCodeValue value;

    @Getter
    private final AuthorizationCodeChallenge challenge;

    @Getter
    private final ExpiredAt expiredAt;

    private AuthorizationCode(final AuthorizationCodeValue value, final AuthorizationCodeChallenge challenge, final ExpiredAt expiredAt) {
        this.value = value;
        this.challenge = challenge;
        this.expiredAt = expiredAt;
    }

    public static AuthorizationCode of(final AuthorizationCodeValue value, final AuthorizationCodeChallenge challenge, final ExpiredAt expiredAt) {
        final AuthorizationCode authorizationCode = new AuthorizationCode(value, challenge, expiredAt);
        return authorizationCode;
    }
}
