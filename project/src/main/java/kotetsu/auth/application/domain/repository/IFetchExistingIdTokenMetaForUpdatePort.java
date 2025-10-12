package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ExistingIdTokenMeta;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public interface IFetchExistingIdTokenMetaForUpdatePort {
    Optional<ExistingIdTokenMeta> fetchForUpdate(LinkedIdTokenCoreKey linkedIdTokenCoreKey);
}
