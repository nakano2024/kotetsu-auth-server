package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingIdTokenCore;

public interface IStoreIdTokenCorePort {
    void store(PendingIdTokenCore tokenBody);
}
