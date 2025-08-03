package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.IdTokenBody;

public interface IStoreIdTokenBodyPort {
    void store(IdTokenBody tokenBody);
}
