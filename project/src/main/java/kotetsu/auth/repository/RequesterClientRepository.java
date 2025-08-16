package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import kotetsu.auth.application.domain.entity.RequesterClient;
import kotetsu.auth.application.domain.repository.IFetchRequesterClientPort;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.ClientRedirectUri;
import kotetsu.auth.application.domain.value.Key;

public class RequesterClientRepository implements IFetchRequesterClientPort {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public RequesterClientRepository(final NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<RequesterClient> fetch(final ClientId clientId) {
        final String sql = """
            SELECT key, client_id, redirect_uri
            FROM clients
            WHERE client_id = :client_id
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId.getValue());
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(RequesterClient.of(
            Key.of(String.valueOf(row.get("key"))),
            ClientId.of((String) row.get("client_id")),
            ClientRedirectUri.of((String) row.get("redirect_uri"))
        ));
    }
}