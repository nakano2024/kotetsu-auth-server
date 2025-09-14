package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.AudienceClient;
import kotetsu.auth.application.domain.repository.IFetchAudienceClientPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Key;

public class AudienceClientRepository implements IFetchAudienceClientPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AudienceClientRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<AudienceClient> fetch(Key key) {
        final String sql = """
            SELECT key, client_id
            FROM clients
            WHERE key = :key;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", key.getValue());
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(AudienceClient.of(
            Key.of(String.valueOf(row.get("key"))),
            ClientId.of((String) row.get("client_id"))
        ));
    }
}
