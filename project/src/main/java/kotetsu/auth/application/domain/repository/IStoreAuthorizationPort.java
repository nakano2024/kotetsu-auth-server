package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.Authorization;

public interface IStoreAuthorizationPort {
    void store(Authorization authorization);
}
