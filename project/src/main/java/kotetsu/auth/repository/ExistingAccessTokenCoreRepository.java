package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.ExistingAccessTokenCore;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.RequestedScopeRelatedAudienceList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenCorePort;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
import kotetsu.auth.application.domain.value.Subject;

public class ExistingAccessTokenCoreRepository implements IFetchExistingAccessTokenCorePort {
    private final NamedParameterJdbcTemplate template;

    public ExistingAccessTokenCoreRepository(final NamedParameterJdbcTemplate template) {
        this.template = template;
    }
    
    @Override
    public Optional<ExistingAccessTokenCore> fetch(Key key) {
        final String sql = """
            SELECT atc.key, atc.issuer, atc.subject, s.key AS s_key, s.name AS s_name, rs.url AS rs_url 
            FROM access_token_cores AS atc
            JOIN access_token_core_scopes AS atcs ON atc.key = atcs.access_token_core_key
            JOIN scopes AS s ON atcs.scope_key = s.key
            LEFT JOIN scope_audiences AS sa ON s.key = sa.scope_key
            LEFT JOIN resource_servers AS rs ON sa.resource_server_key = rs.key
            WHERE atc.key = :key
            ORDER BY s.created_at ASC;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(key.getValue()));

        final List<Map<String, Object>> rows = template.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Set<Scope> scopes = rows.stream()
            .map(row -> Scope.of(
                Key.of((String) row.get("s_key")),
                ScopeName.of((String) row.get("s_name")))
            )
            .collect(Collectors.toSet());
        final RequestedScopeList requestedScopeList = RequestedScopeList.of(scopes);

        final Set<String> resourceServerUrls = rows.stream()
            .map(row -> (String) row.get("rs_url"))
            .collect(Collectors.toSet());
        final RequestedScopeRelatedAudienceList scopeRelatedAudienceList = RequestedScopeRelatedAudienceList.of(resourceServerUrls);

        return Optional.of(ExistingAccessTokenCore.of(
            Key.of((String) rows.get(0).get("key")),
            Issuer.of((String) rows.get(0).get("issuer")),
            Subject.of(String.valueOf(rows.get(0).get("subject"))),
            requestedScopeList,
            scopeRelatedAudienceList
        ));
    }
}