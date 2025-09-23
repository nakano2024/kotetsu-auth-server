package kotetsu.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.util.IGenerateAuthorizationCodeValuePort;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

@Component
public class AuthorizationCodeValueGenerator implements IGenerateAuthorizationCodeValuePort {
    @Override
    public AuthorizationCodeValue generate() {
        byte[] randomBytes = new byte[AuthorizationCodeValue.LENGTH];

        new SecureRandom().nextBytes(randomBytes);
        final String authorizationCodeValueString = Base64.getEncoder().withoutPadding().encodeToString(randomBytes);

        return AuthorizationCodeValue.of(authorizationCodeValueString);
    }
}
