package kotetsu.auth.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ResourceOwnerKeyData;
import kotetsu.auth.application.query.IFindResourceOwnerKeyPort;

@Component
public class ResourceOwnerKeyQueryService implements IFindResourceOwnerKeyPort{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ResourceOwnerKeyQueryService(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ResourceOwnerKeyData> findByResourceOwnerKey(String resourceOwnerKey) {
        final String sql = """
            SELECT key FROM users WHERE key = :key;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(resourceOwnerKey));
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);
        return Optional.of(ResourceOwnerKeyData.of(String.valueOf(row.get("key"))));
    }
}
