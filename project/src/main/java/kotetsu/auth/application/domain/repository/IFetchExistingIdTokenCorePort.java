package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchExistingIdTokenCorePort {
    ExistingIdTokenCore fetch(Key key);
}
