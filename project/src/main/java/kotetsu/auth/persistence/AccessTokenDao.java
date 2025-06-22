package kotetsu.auth.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.store.AccessTokenStore;
import kotetsu.auth.application.persistence.IStoreAccessTokenPort;

@Component
public class AccessTokenDao implements IStoreAccessTokenPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccessTokenDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String store(final AccessTokenStore accessToken) {
        // TODO: Implement database storage logic
        return null;
    }
}