package kotetsu.auth.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.AccessTokenDraftData;
import kotetsu.auth.application.dto.data.ResourceServerData;
import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.dto.store.AccessTokenDraftStore;
import kotetsu.auth.application.persistence.IFindAccessTokenDraftByIdPort;
import kotetsu.auth.application.persistence.IStoreAccessTokenDraftPort;

@Component
public class AccessTokenDraftDao implements IFindAccessTokenDraftByIdPort, IStoreAccessTokenDraftPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccessTokenDraftDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AccessTokenDraftData findById(final UUID code) {

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);

        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("""
                SELECT atd.code, atd.issuer, atd.subject, scopes.code as scope_code, scopes.name as scope_name, rs.code as rs_code, rs.name as rs_name, rs.url as rs_url
                FROM access_token_drafts as atd
                INNER JOIN access_token_draft_scopes AS atds ON atd.code = atds.access_token_draft_code
                INNER JOIN scopes ON atds.scope_code = scopes.code
                INNER JOIN resource_servers as rs ON scopes.resource_server_code = rs.code
                WHERE atd.code = :code;
            """, params);

            if (rows.isEmpty()) {
                throw new EmptyResultDataAccessException(1);
            }

            final List<ScopeData> scopes = new ArrayList<>();
            rows.forEach(row -> {
                scopes.add(ScopeData.of(
                    (UUID) row.get("scope_code"),
                    (String) row.get("scope_name")
                ));
            });

            final List<ResourceServerData> resourceServers = new ArrayList<>();
            rows.forEach(row -> {
                resourceServers.add(ResourceServerData.of(
                    (UUID) row.get("rs_code"),
                    (String) row.get("rs_name"),
                    (String) row.get("rs_url")
                ));
            });

            return AccessTokenDraftData.of(
                (UUID) rows.get(0).get("code"),
                (String) rows.get(0).get("issuer"),
                (UUID) rows.get(0).get("subject"),
                scopes,
                resourceServers
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UUID store(AccessTokenDraftStore accessTokenDraft) {
        final UUID accessTokenDraftCode = UUID.randomUUID();

        // Insert access token draft
        final Map<String, Object> params = new HashMap<>();
        params.put("code", accessTokenDraftCode);
        params.put("issuer", accessTokenDraft.getIssuer());
        params.put("subject", accessTokenDraft.getSubject());

        jdbcTemplate.update("""
            INSERT INTO access_token_drafts(code, issuer, subject)
            VALUES(:code, :issuer, :subject)
        """, params);

        final List<SqlParameterSource> batchArgs = new ArrayList<>();
        accessTokenDraft.getScopeCodes().forEach(scopeCode -> {
            batchArgs.add(
                new MapSqlParameterSource()
                    .addValue("access_token_draft_code", accessTokenDraftCode)
                    .addValue("scope_code", scopeCode)
            );
        });

        jdbcTemplate.batchUpdate("""
            INSERT INTO access_token_draft_scopes(access_token_draft_code, scope_code)
            VALUES(:access_token_draft_code, :scope_code)
        """, batchArgs.toArray(SqlParameterSource[]::new));

        return accessTokenDraftCode;
    }
}
