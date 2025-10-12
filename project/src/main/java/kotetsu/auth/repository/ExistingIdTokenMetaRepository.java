package kotetsu.auth.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.ExistingIdTokenMeta;
import kotetsu.auth.application.domain.repository.IDeleteExistingIdTokenMetaPort;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenMetaForUpdatePort;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;

@Component
public class ExistingIdTokenMetaRepository
    implements IFetchExistingIdTokenMetaForUpdatePort,
        IDeleteExistingIdTokenMetaPort
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExistingIdTokenMetaRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ExistingIdTokenMeta> fetchForUpdate(LinkedIdTokenCoreKey linkedIdTokenCoreKey) {
        final String sql = """
            SELECT key, issued_at, expired_at
            FROM id_token_metas
            WHERE id_token_core_key = :id_token_core_key
            FOR UPDATE;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("id_token_core_key", UUID.fromString(linkedIdTokenCoreKey.getValue()));

        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingIdTokenMeta.of(
            Key.of(String.valueOf(row.get("key"))),
            Duration.of(
                IssuedAt.of((Date) row.get("issued_at")),
                ExpiredAt.of((Date) row.get("expired_at"))
            ),
            IdTokenUniqueId.of(String.valueOf(row.get("key")))
        )); 
    }

    @Override
    public void delete(ExistingIdTokenMeta idTokenMeta) {
        final String sql = """
            DELETE FROM id_token_metas
            WHERE key = :key
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(idTokenMeta.getKey().getValue()));

        jdbcTemplate.update(sql, params);
    }
}
