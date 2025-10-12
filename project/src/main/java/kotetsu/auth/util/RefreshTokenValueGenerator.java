package kotetsu.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IGenerateRefreshTokenValuePort;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

@Component
public class RefreshTokenValueGenerator implements IGenerateRefreshTokenValuePort {
@Override
public RefreshTokenValue generate() {
    byte[] randomBytes = new byte[RefreshTokenValue.LENGTH];
    new SecureRandom().nextBytes(randomBytes);

    final String refreshTokenString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

    return RefreshTokenValue.of(refreshTokenString);
}
}
