package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;

public interface IFetchExistingAuthorizationPort {
    Optional<ExistingAuthorization> fetch(AuthorizationCodeValue authorizationCodeValue);
}
