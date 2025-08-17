package kotetsu.auth.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.ExistingRefreshToken;
import kotetsu.auth.application.domain.repository.IDeleteExistingRefreshTokenPort;
import kotetsu.auth.application.domain.repository.IFetchExistingRefreshTokenPort;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.ExpiredAt;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.IssuedAt;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import kotetsu.auth.application.domain.value.RefreshTokenValue;

public class ExistingRefreshTokenRepository
    implements IFetchExistingRefreshTokenPort,
        IDeleteExistingRefreshTokenPort
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExistingRefreshTokenRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ExistingRefreshToken> fetch(RefreshTokenValue value) {
        final String sql = """
            SELECT refresh_token_core_key, grant_type_name, issued_at, expired_at
            FROM refresh_tokens
            value = :value;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("value", UUID.fromString(value.getValue()));

        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingRefreshToken.of(
            LinkedRefreshTokenCoreKey.of(String.valueOf(row.get("refresh_token_core_key"))),
            Duration.of(
                IssuedAt.of((Date) row.get("issued_at")),
                ExpiredAt.of((Date) row.get("expired_at"))
            ),
            GrantType.of(String.valueOf(row.get("grant_type_name")))
        )); 
    }

    @Override
    public void delete(ExistingRefreshToken existingRefreshToken) {
        // TODO Auto-generated method stub
        
    }
}
