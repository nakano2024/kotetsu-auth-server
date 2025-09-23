package kotetsu.auth.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.PendingAccessTokenCore;
import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.entity.Scope;
import kotetsu.auth.application.domain.repository.IStorePendingAccessTokenCorePort;
import kotetsu.auth.application.domain.value.Key;

@Component
public class PendingAccessTokenCoreRepository implements IStorePendingAccessTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PendingAccessTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void store(PendingAccessTokenCore accessTokenCore) {
        final String sql = """
            INSERT INTO access_token_cores(
                key,
                issuer,
                subject,
                requester_client_id
            )
            VALUES(
                :key,
                :issuer,
                :subject,
                :requester_client_id
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(accessTokenCore.getKey().getValue()));
        params.put("issuer", accessTokenCore.getIssuer().getValue());
        params.put("subject", accessTokenCore.getSubject().getValue());
        params.put("requester_client_id", accessTokenCore.getRequesterClientId().getValue());

        jdbcTemplate.update(sql, params);

        storeRequestedScopes(accessTokenCore.getKey(), accessTokenCore.getRequestedScopeList());

    }

    private void storeRequestedScopes(final Key key, final RequestedScopeList requestedScopeList) {
        final String sql = """
            INSERT INTO access_token_core_scopes(
                access_token_core_key,
                scope_key
            )
            VALUES(
                :access_token_core_key,
                :scope_key
            );
        """;

        List<Map<String, Object>> paramsList = new ArrayList<>();
        for (final Scope scope : requestedScopeList.getScopes()) {
            final Map<String, Object> params = new HashMap<>();
            params.put("access_token_core_key", UUID.fromString(key.getValue()));
            params.put("scope_key", UUID.fromString(scope.getKey().getValue()));
            paramsList.add(params);
        }

        final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(paramsList.toArray());

        jdbcTemplate.batchUpdate(sql, batch);
    }
}