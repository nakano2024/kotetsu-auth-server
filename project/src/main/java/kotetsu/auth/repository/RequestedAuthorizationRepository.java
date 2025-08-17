package kotetsu.auth.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.repository.IStoreRequestedAuthorizationPort;

public class RequestedAuthorizationRepository implements IStoreRequestedAuthorizationPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RequestedAuthorizationRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void store(RequestedAuthorization authorization) {
        
    }
}