package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.PendingRefreshTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingRefreshTokenCorePort;

public class PendingRefreshTokenCoreRepository implements IStorePendingRefreshTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PendingRefreshTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    

    @Override
    public void store(final PendingRefreshTokenCore refreshTokenCore) {
        final String sql = """
            INSERT INTO refresh_token_cores(
                key,
                access_token_core_key,
                id_token_core_key
            )
            VALUES(
                :key,
                :access_token_core_key,
                :id_token_core_key
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(refreshTokenCore.getKey().getValue()));
        params.put("access_token_core_key", refreshTokenCore.getLinkedAccessTokenCoreId().getValue());
        params.put("id_token_core_key", refreshTokenCore.getLinkedIdTokenCoreId().getValue());

        jdbcTemplate.update(sql, params);
    }
}