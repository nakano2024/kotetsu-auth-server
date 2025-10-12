package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingRefreshToken;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

public interface IFetchExistingRefreshTokenForUpdatePort {
    Optional<ExistingRefreshToken> fetchForUpdate(RefreshTokenValue value);
}
