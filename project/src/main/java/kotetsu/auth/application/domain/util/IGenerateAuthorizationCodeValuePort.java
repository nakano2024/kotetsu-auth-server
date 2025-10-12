package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public interface IGenerateAuthorizationCodeValuePort {
    AuthorizationCodeValue generate();
}
