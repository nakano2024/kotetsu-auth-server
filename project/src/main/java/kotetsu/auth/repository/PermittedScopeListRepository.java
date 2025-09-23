package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.PermittedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IFetchPermittedScopeListPort;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;

@Component
public class PermittedScopeListRepository implements IFetchPermittedScopeListPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PermittedScopeListRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<PermittedScopeList> fetch(final Key clientKey) {
        final String sql = """
            SELECT s.key AS s_key, s.name AS s_name
            FROM client_permitted_scopes as cps
            JOIN scopes AS s ON cps.scope_key = s.key
            WHERE cps.client_key = :client_key
            ORDER BY s.created_at ASC;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("client_key", UUID.fromString(clientKey.getValue()));
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        Set<Scope> scopes = rows.stream()
            .map(row -> Scope.of(
                Key.of(String.valueOf(row.get("s_key"))),
                ScopeName.of(String.valueOf(row.get("s_name")))
            ))
            .collect(Collectors.toSet());

        return Optional.of(PermittedScopeList.of(scopes));
    }
}
