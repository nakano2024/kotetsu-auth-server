package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.ExistingRefreshTokenCore;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenCorePort;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

public class ExistingRefreshTokenCoreRepository implements IFetchExistingRefreshTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExistingRefreshTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ExistingRefreshTokenCore> fetch(Key key) {
        final String sql = """
            SELECT key, access_token_core_key, id_token_core_key
            FROM refresh_token_cores
            WHERE key = :key
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(key.getValue()));
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingRefreshTokenCore.of(
            Key.of(String.valueOf(row.get("key"))),
            LinkedAccessTokenCoreKey.of(String.valueOf(row.get("access_token_core_key"))),
            LinkedIdTokenCoreKey.of(String.valueOf(row.get("id_token_core_key")))
        ));
    }
}