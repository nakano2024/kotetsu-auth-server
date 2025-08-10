package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;

public interface IDeleteExistingAccessTokenPort {
    void delete(ExistingAccessToken accessToken);
}
