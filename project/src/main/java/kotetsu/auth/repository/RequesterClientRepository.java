package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.ClientId;

public class RequesterClientRepository implements IFetchRequesterClientPort {
    
    @Override
    public Optional<RequesterClient> fetch(final ClientId clientId) {
        return Optional.empty();
    }
}