package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface  IFetchExistingRefreshTokenCorePort {
    ExistingRefreshTokenCore fetch(Key key);
}
