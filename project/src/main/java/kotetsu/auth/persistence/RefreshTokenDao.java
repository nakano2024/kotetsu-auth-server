package kotetsu.auth.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.store.RefreshTokenStore;
import kotetsu.auth.application.persistence.IStoreRefreshTokenPort;

@Component
public class RefreshTokenDao implements IStoreRefreshTokenPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RefreshTokenDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String store(final RefreshTokenStore refreshToken) {
        // TODO: Implement database storage logic
        return null;
    }
}
