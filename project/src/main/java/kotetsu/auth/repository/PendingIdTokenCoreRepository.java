package kotetsu.auth.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.PendingIdTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingIdTokenCorePort;

public class PendingIdTokenCoreRepository implements IStorePendingIdTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PendingIdTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void store(PendingIdTokenCore tokenBody) {
        
    }
}