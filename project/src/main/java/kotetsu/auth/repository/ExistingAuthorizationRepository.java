package kotetsu.auth.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.ExistingAuthorization;
import kotetsu.auth.application.domain.repository.IDeleteExistingAuthorization;
import kotetsu.auth.application.domain.repository.IFetchExistingAuthorizationForUpdatePort;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.AuthorizationCodeChallenge;
import kotetsu.auth.application.domain.value.AuthorizationCodeValue;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;

public class ExistingAuthorizationRepository 
    implements IDeleteExistingAuthorization,
        IFetchExistingAuthorizationForUpdatePort {
    
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExistingAuthorizationRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void delete(ExistingAuthorization existingAuthorization) {
        final String sql = """
            DELETE FROM authorization_codes
            WHERE key = :key
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(existingAuthorization.getKey().getValue()));

        jdbcTemplate.update(sql, params);
    }
    
    @Override
    public Optional<ExistingAuthorization> fetchForUpdate(final AuthorizationCodeValue authorizationCodeValue) {
        final String sql = """
            SELECT key, value, challenge, expired_at, access_type_name, grant_type_name, access_token_core_key, id_token_core_key, refresh_token_core_key
            FROM authorization_codes
            WHERE value = :value
            FOR UPDATE;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("value", authorizationCodeValue.getValue());
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingAuthorization.of(
            Key.of(String.valueOf(row.get("key"))),
            AuthorizationCode.of(
                AuthorizationCodeValue.of((String) row.get("value")),
                AuthorizationCodeChallenge.of((String) row.get("challenge")),
                ExpiredAt.of((Date) row.get("expired_at"))
            ),
            AccessType.of((String) row.get("access_type_name")),
            LinkedAccessTokenCoreKey.of(String.valueOf(row.get("access_token_core_key"))), // UUID型をStringに変換するための書き方
            LinkedIdTokenCoreKey.of(String.valueOf(row.get("id_token_core_key"))),
            LinkedRefreshTokenCoreKey.of(String.valueOf(row.get("refresh_token_core_key"))),
            GrantType.of((String) row.get("grant_type_name"))
        ));
    }
}