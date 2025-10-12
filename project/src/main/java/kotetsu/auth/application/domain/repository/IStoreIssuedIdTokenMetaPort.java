package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;

public interface IStoreIssuedIdTokenMetaPort {
    void store(IssuedIdTokenMeta idTokenMeta);
}
