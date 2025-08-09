package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchRequesterClientPort {
    Optional<RequesterClient> fetch(Key key);
}
