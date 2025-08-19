package kotetsu.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

import kotetsu.auth.application.domain.util.IGenerateRefreshTokenValuePort;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

public class RefreshTokenValueGenerator implements IGenerateRefreshTokenValuePort {
@Override
public RefreshTokenValue generate() {
    byte[] randomBytes = new byte[RefreshTokenValue.LENGTH];
    new SecureRandom().nextBytes(randomBytes);

    final String refreshTokenString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

    return RefreshTokenValue.of(refreshTokenString);
}
}
