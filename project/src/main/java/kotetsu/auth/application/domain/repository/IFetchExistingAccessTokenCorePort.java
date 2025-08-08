package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchExistingAccessTokenCorePort {
    ExistingAccessTokenCore fetch(Key key);
}
