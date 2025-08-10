package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.AudienceClient;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchAudienceClientPort {
    Optional<AudienceClient> fetch(Key key);
}
