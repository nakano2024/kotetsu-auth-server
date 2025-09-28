package kotetsu.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IGenerateAccessTokenValuePort;
import kotetsu.auth.application.domain.value.AccessTokenValue;

@Component
public class AccessTokenValueGenerator implements IGenerateAccessTokenValuePort {
    @Override
    public AccessTokenValue generate(int length) {
        byte[] randomBytes = new byte[length];
        new SecureRandom().nextBytes(randomBytes);
        final String tokenValueString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return AccessTokenValue.of(tokenValueString);
    }
}
