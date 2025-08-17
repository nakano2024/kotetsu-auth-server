package kotetsu.auth.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IFetchRequestedScopeListPort;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.ScopeName;

public class RequestedScopeListRepository implements IFetchRequestedScopeListPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RequestedScopeListRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }    
    
    @Override
    public Optional<RequestedScopeList> fetch(final RequestedScopeNameList scopeNameList) {
        final List<String> scopeNameStrings = scopeNameList.getValue()
            .stream()
            .map(scopeName -> scopeName.getValue())
            .collect(Collectors.toList());

        final String sql = """
            SELECT key, name
            FROM scopes
            WHERE name IN (:scope_names)
            ORDER BY created_at ASC;
        """;

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("scope_names", scopeNameStrings);
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        final Set<Scope> scopes = rows.stream()
            .map(row -> Scope.of(
                Key.of(String.valueOf(row.get("key"))),
                ScopeName.of(String.valueOf(row.get("name")))
            ))
            .collect(Collectors.toSet());

        return Optional.of(RequestedScopeList.of(scopes));
    }
}
