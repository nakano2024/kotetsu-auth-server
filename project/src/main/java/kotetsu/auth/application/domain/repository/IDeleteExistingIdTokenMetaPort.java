package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ExistingIdTokenMeta;

public interface IDeleteExistingIdTokenMetaPort {
    void delete(ExistingIdTokenMeta idTokenMeta);
}
