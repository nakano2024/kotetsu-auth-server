package kotetsu.auth.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.persistence.IFindPermittedScopeListByClientCodePort;
import kotetsu.auth.application.persistence.IFindScopeListByScopeNameListPort;

@Component
public class ScopeDao implements
    IFindPermittedScopeListByClientCodePort,
    IFindScopeListByScopeNameListPort
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScopeDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ScopeData> findByClientCode(final UUID clientInformationCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_information_code", clientInformationCode);

        List<Map<String, Object>> results = jdbcTemplate.queryForList("""
            SELECT s.code, s.name
            FROM scopes as s
            INNER JOIN client_permitted_scopes as cps ON s.code = cps.scope_code
            WHERE cps.client_information_code = :client_information_code
        """, params);

        return results.stream()
            .map(row -> ScopeData.of(
                (UUID) row.get("code"),
                (String) row.get("name")
            ))
            .toList();
    }

    @Override
    public List<ScopeData> findByScopeNames(List<String> scopeNames) {
        if (scopeNames == null || scopeNames.isEmpty()) {
            return List.of();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("scope_names", scopeNames);

        List<Map<String, Object>> results = jdbcTemplate.queryForList("""
            SELECT code, name, resource_server_code
            FROM scopes
            WHERE name IN (:scope_names)
        """, params);

        return results.stream()
            .map(row -> ScopeData.of(
                (UUID) row.get("code"),
                (String) row.get("name")
            ))
            .toList();
    }
}
