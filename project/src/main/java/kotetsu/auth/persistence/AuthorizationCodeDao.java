package kotetsu.auth.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.AuthorizationCodeData;
import kotetsu.auth.application.dto.store.AuthorizationCodeStore;
import kotetsu.auth.application.persistence.IDeleteAuthorizationCodePort;
import kotetsu.auth.application.persistence.IFindAuthorizationCodeByValuePort;
import kotetsu.auth.application.persistence.IStoreAuthorizationCodePort;

@Component
public class AuthorizationCodeDao implements IDeleteAuthorizationCodePort, IFindAuthorizationCodeByValuePort, IStoreAuthorizationCodePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorizationCodeDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteByValue(final String value) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", value);

        jdbcTemplate.update("""
            DELETE FROM authorization_codes WHERE value = :code
        """, params);
    }

    @Override
    public AuthorizationCodeData findByValue(final String value) {
        Map<String, Object> params = new HashMap<>();
        params.put("value", value);

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap("""
                SELECT value, challenge, access_token_draft_code, id_token_draft_code, issued_at, expired_at, enable_openid, enable_offline_access
                FROM authorization_codes
                WHERE value = :value
            """, params);

            return AuthorizationCodeData.of(
                (String) result.get("value"),
                (String) result.get("challenge"),
                (UUID) result.get("access_token_draft_code"),
                (UUID) result.get("id_token_draft_code"),
                (java.util.Date) result.get("issued_at"),
                (java.util.Date) result.get("expired_at"),
                (Boolean) result.get("enable_openid"),
                (Boolean) result.get("enable_offline_access")
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String store(AuthorizationCodeStore authorizationCode) {
        final UUID authorizationCodeUuid = UUID.randomUUID();

        final Map<String, Object> params = new HashMap<>();
        params.put("code", authorizationCodeUuid);
        params.put("value", authorizationCode.getValue());
        params.put("challenge", authorizationCode.getChallenge());
        params.put("access_token_draft_code", authorizationCode.getAccessTokenDraftCode());
        params.put("id_token_draft_code", authorizationCode.getIdTokenDraftCode());
        params.put("enable_openid", authorizationCode.isEnableOpenid());
        params.put("enable_offline_access", authorizationCode.isEnableOfflineAccess());
        params.put("issued_at", authorizationCode.getIssuedAt());
        params.put("expired_at", authorizationCode.getExpiredAt());

        jdbcTemplate.update("""
            INSERT INTO authorization_codes(
                code, value, challenge, access_token_draft_code, id_token_draft_code,
                enable_openid, enable_offline_access, issued_at, expired_at
            )
            VALUES(
                :code, :value, :challenge, :access_token_draft_code, :id_token_draft_code,
                :enable_openid, :enable_offline_access, :issued_at, :expired_at
            )
        """, params);

        return authorizationCode.getValue();
    }
}
