package kotetsu.auth.application.domain.util;

import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public interface IFetchGenerateAuthorizationCodeValuePort {
    AuthorizationCodeValue generate();
}
