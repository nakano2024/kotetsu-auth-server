package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.PendingIdTokenCore;

public interface IStorePendingIdTokenCorePort {
    void store(PendingIdTokenCore tokenBody);
}
