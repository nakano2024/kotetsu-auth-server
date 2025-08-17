package kotetsu.auth.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingRefreshTokenCorePort;

public class PendingRefreshTokenCoreRepository implements IStorePendingRefreshTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PendingRefreshTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    

    @Override
    public void store(PendingRefreshTokenCore refreshTokenBody) {
        
    }
}