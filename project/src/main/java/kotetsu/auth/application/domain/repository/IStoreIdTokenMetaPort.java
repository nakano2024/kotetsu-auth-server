package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.IdTokenMeta;

public interface IStoreIdTokenMetaPort {
    void store(IdTokenMeta idTokenMeta);
}
