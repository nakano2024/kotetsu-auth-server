package kotetsu.auth.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.dto.data.ClientCredentialData;
import kotetsu.auth.application.query.IFindClientCredentialPort;

public class ClientCredentialQueryService implements IFindClientCredentialPort{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClientCredentialQueryService(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ClientCredentialData> findByClientId(String clientId) {
        final String sql = """
            SELECT client_id, client_secret_hash
            FROM clients
            WHERE client_id = :client_id;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ClientCredentialData.of(
            String.valueOf(row.get("client_id")),
            String.valueOf(row.get("client_secret_hash"))
        ));
    }
}
