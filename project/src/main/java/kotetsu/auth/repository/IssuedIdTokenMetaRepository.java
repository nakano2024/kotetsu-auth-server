package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.IssuedIdTokenMeta;
import kotetsu.auth.application.domain.repository.IStoreIssuedIdTokenMetaPort;

@Component
public class IssuedIdTokenMetaRepository
    implements IStoreIssuedIdTokenMetaPort
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IssuedIdTokenMetaRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void store(IssuedIdTokenMeta idTokenMeta) {
        final String sql = """
            INSERT INTO id_token_metas(
                key,
                id_token_core_key,
                issued_at,
                expired_at
            )
            VALUES(
                :key,
                :id_token_core_key,
                :issued_at,
                :expired_at
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(idTokenMeta.getUniqueId().getValue()));
        params.put("id_token_core_key", UUID.fromString(idTokenMeta.getLinkedIdTokenCoreKey().getValue()));
        params.put("issued_at", idTokenMeta.getDuration().getIssuedAt().getValue());
        params.put("expired_at", idTokenMeta.getDuration().getExpiredAt().getValue());

        jdbcTemplate.update(sql, params);
    }
}
