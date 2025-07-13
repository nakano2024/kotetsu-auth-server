package kotetsu.auth.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.store.AccessTokenStore;
import kotetsu.auth.application.persistence.IStoreAccessTokenPort;

@Component
public class AccessTokenDao implements IStoreAccessTokenPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccessTokenDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String store(final AccessTokenStore accessToken) {
        final Map<String, Object> params = new HashMap<>();
        final UUID accessTokenCode = UUID.randomUUID();
        params.put("code", accessTokenCode);
        params.put("value", accessToken.getValue());
        params.put("issuer", accessToken.getIssuer());
        params.put("subject", accessToken.getSubject());
        params.put("issued_at", accessToken.getIssuedAt());
        params.put("expired_at", accessToken.getExpiredAt());

        jdbcTemplate.update("""
            INSERT INTO access_tokens(code, value, issuer, subject, issued_at, expired_at)
            VALUES(:code, :value, :issuer, :subject, :issued_at, :expired_at);
        """, params);

        final List<SqlParameterSource> batchArgs = new ArrayList<>();
        accessToken.getScopeCodes().forEach(scopeCode -> {
            batchArgs.add(
                new MapSqlParameterSource()
                    .addValue("access_token_code", accessTokenCode)
                    .addValue("scope_code", scopeCode)
            );
        });

        jdbcTemplate.batchUpdate("""
            INSERT INTO access_token_scopes(access_token_code, scope_code)
            VALUES(:access_token_code, :scope_code);
        """, batchArgs.toArray(SqlParameterSource[]::new));

        return accessToken.getValue();
    }
}
