package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.RequestedAuthorization;

public interface IStoreRequestedAuthorizationPort {
    void store(RequestedAuthorization authorization);
}
