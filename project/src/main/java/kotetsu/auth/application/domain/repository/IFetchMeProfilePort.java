package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.value.Key;

public interface IFetchMeProfilePort {
    Optional<MeProfile> fetch(Key key);
}
