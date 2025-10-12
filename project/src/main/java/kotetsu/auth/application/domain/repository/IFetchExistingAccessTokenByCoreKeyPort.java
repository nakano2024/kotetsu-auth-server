package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

public interface IFetchExistingAccessTokenByCoreKeyPort {
    Optional<ExistingAccessToken> fetchForUpdateByCoreKey(LinkedAccessTokenCoreKey linkedAccessTokenCoreKey);
}
