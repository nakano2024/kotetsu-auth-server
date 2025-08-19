package kotetsu.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

import kotetsu.auth.application.domain.util.IGenerateAccessTokenValuePort;
import kotetsu.auth.application.domain.value.AccessTokenValue;

public class AccessTokenValueGenerator implements IGenerateAccessTokenValuePort {
    @Override
    public AccessTokenValue generate(int length) {
        byte[] randomBytes = new byte[258];
        new SecureRandom().nextBytes(randomBytes);
        final String tokenValueString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return AccessTokenValue.of(tokenValueString);
    }
}
