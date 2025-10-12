package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingRefreshToken;

public interface IDeleteExistingRefreshTokenPort {
    void delete(ExistingRefreshToken existingRefreshToken);
}
