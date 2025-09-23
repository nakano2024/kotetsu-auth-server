package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.IssuedAccessToken;
import kotetsu.auth.application.domain.repository.IStoreIssuedAccessTokenPort;

@Component
public class IssuedAccessTokenRepository implements IStoreIssuedAccessTokenPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IssuedAccessTokenRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void store(IssuedAccessToken issuedAccessToken) {
        final String sql = """
            INSERT INTO authorization_codes(
                value,
                access_token_core_key,
                issued_at,
                expired_at
            )
            VALUES(
                :value,
                :access_token_core_key,
                :issued_at,
                :expired_at
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("value", issuedAccessToken.getValue().getValue());
        params.put("access_token_core_key", UUID.fromString(issuedAccessToken.getLinkedAccessTokenCoreKey().getValue()));
        params.put("issued_at", issuedAccessToken.getDuration().getIssuedAt().getValue());
        params.put("expired_at", issuedAccessToken.getDuration().getExpiredAt().getValue());

        jdbcTemplate.update(sql, params);
    }
}