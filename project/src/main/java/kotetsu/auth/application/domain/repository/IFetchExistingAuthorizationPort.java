package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public interface IFetchExistingAuthorizationPort {
    ExistingAuthorization fetch(AuthorizationCodeValue authorizationCodeValue);
}
