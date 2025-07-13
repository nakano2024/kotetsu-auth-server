package kotetsu.auth.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.store.RefreshTokenStore;
import kotetsu.auth.application.persistence.IStoreRefreshTokenPort;

@Component
public class RefreshTokenDao implements IStoreRefreshTokenPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RefreshTokenDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String store(final RefreshTokenStore refreshToken) {
        final UUID refreshTokenCode = UUID.randomUUID();

        final Map<String, Object> params = new HashMap<>();
        params.put("code", refreshTokenCode);
        params.put("value", refreshToken.getCode());
        params.put("access_token_draft_code", refreshToken.getAccessTokenDraftCode());
        params.put("id_token_draft_code", refreshToken.getIdTokenDraftCode());
        params.put("issued_at", refreshToken.getIssuedAt());
        params.put("expired_at", refreshToken.getExpiredAt());

        jdbcTemplate.update("""
            INSERT INTO refresh_tokens(code, value, access_token_draft_code, id_token_draft_code, issued_at, expired_at)
            VALUES(:code, :value, :access_token_draft_code, :id_token_draft_code, :issued_at, :expired_at)
        """, params);

        return refreshToken.getCode();
    }
}
