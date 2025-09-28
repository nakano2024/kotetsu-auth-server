package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.IssuedRefreshToken;
import kotetsu.auth.application.domain.repository.IStoreIssuedRefreshTokenPort;

@Component
public class IssuedRefreshTokenRepository implements IStoreIssuedRefreshTokenPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IssuedRefreshTokenRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void store(final IssuedRefreshToken issuedRefreshToken) {
        final String sql = """
            INSERT INTO refresh_tokens(
                value,
                refresh_token_core_key,
                grant_type_name,
                issued_at,
                expired_at
            )
            VALUES(
                :value,
                :refresh_token_core_key,
                :grant_type_name,
                :issued_at,
                :expired_at
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("value", issuedRefreshToken.getValue().getValue());
        params.put("refresh_token_core_key", UUID.fromString(issuedRefreshToken.getLinkedRefreshTokenCoreKey().getValue()));
        params.put("grant_type_name", issuedRefreshToken.getGrantType().getValue());
        params.put("issued_at", issuedRefreshToken.getDuration().getIssuedAt().getValue());
        params.put("expired_at", issuedRefreshToken.getDuration().getExpiredAt().getValue());

        jdbcTemplate.update(sql, params);
    }
}