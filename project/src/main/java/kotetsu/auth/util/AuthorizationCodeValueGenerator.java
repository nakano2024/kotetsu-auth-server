package kotetsu.auth.util;

import java.security.SecureRandom;
import java.util.Base64;

import kotetsu.auth.application.domain.util.IFetchGenerateAuthorizationCodeValuePort;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public class AuthorizationCodeValueGenerator implements IFetchGenerateAuthorizationCodeValuePort {
    @Override
    public AuthorizationCodeValue generate() {
        byte[] randomBytes = new byte[AuthorizationCodeValue.LENGTH];

        new SecureRandom().nextBytes(randomBytes);
        final String authorizationCodeValueString = Base64.getEncoder().withoutPadding().encodeToString(randomBytes);

        return AuthorizationCodeValue.of(authorizationCodeValueString);
    }
}
