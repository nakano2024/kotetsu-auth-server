package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.Key;

public class RequesterClientRepository implements IFetchRequesterClientPort {
    
    @Override
    public Optional<RequesterClient> fetch(Key key) {
        return Optional.empty();
    }
}