package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.value.ClientId;

public interface IFetchRequesterClientPort {
    Optional<RequesterClient> fetch(ClientId clientId);
}
