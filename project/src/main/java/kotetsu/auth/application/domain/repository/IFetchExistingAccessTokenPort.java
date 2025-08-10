package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.value.AccessTokenValue;

public interface IFetchExistingAccessTokenPort {
    Optional<ExistingAccessToken> fetch(AccessTokenValue value);
    void delete(ExistingAccessToken accessToken);
}
