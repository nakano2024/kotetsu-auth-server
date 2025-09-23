package kotetsu.auth.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.ExistingAccessToken;
import kotetsu.auth.application.domain.repository.IDeleteExistingAccessTokenPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenByCoreKeyPort;
import kotetsu.auth.application.domain.repository.IFetchExistingAccessTokenPort;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;

@Component
public class ExistingAccessTokenRepository
    implements IFetchExistingAccessTokenPort,
    IDeleteExistingAccessTokenPort,
    IFetchExistingAccessTokenByCoreKeyPort
{
    private final NamedParameterJdbcTemplate template;

    public ExistingAccessTokenRepository(final NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<ExistingAccessToken> fetch(AccessTokenValue value) {
        final String sql = """
            SELECT key, access_token_core_key, issued_at, expired_at
            FROM access_tokens
            WHERE value = :value;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("value", value.getValue());

        final List<Map<String, Object>> rows = template.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingAccessToken.of(
            Key.of(String.valueOf(row.get("key"))),
            LinkedAccessTokenCoreKey.of(String.valueOf(row.get("access_token_core_key"))),
            Duration.of(
                IssuedAt.of((Date) row.get("issued_at")),
                ExpiredAt.of((Date) row.get("expired_at"))
            )
        ));
    }

    @Override
    public Optional<ExistingAccessToken> fetchForUpdateByCoreKey(LinkedAccessTokenCoreKey linkedAccessTokenCoreKey) {
        final String sql = """
            SELECT key, access_token_core_key, issued_at, expired_at
            FROM access_tokens
            WHERE access_token_core_key = :access_token_core_key
            FOR UPDATE;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("access_token_core_key", UUID.fromString(linkedAccessTokenCoreKey.getValue()));

        final List<Map<String, Object>> rows = template.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingAccessToken.of(
            Key.of(String.valueOf(row.get("key"))),
            LinkedAccessTokenCoreKey.of(String.valueOf(row.get("access_token_core_key"))),
            Duration.of(
                IssuedAt.of((Date) row.get("issued_at")),
                ExpiredAt.of((Date) row.get("expired_at"))
            )
        ));
    }

    @Override
    public void delete(ExistingAccessToken accessToken) {
        final String sql = """
            DELETE FROM access_tokens
            WHERE key = :key
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(accessToken.getKey().getValue()));

        template.update(sql, params);
    }
}
