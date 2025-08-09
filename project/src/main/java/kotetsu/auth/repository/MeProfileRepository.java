package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.repository.IFetchMeProfilePort;
import kotetsu.auth.application.domain.value.Key;

public class MeProfileRepository implements IFetchMeProfilePort {
    
    @Override
    public Optional<MeProfile> fetch(Key key) {
        return Optional.empty();
    }
}