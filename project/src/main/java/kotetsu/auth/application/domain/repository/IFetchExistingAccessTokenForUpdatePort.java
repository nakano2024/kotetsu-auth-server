package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.value.AccessTokenValue;

public interface IFetchExistingAccessTokenForUpdatePort {
    Optional<ExistingAccessToken> fetchForUpdate(AccessTokenValue value);
}
