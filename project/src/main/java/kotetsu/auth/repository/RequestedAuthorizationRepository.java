package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.RequestedAuthorization;
import kotetsu.auth.application.domain.repository.IStoreRequestedAuthorizationPort;

@Component
public class RequestedAuthorizationRepository implements IStoreRequestedAuthorizationPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RequestedAuthorizationRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void store(RequestedAuthorization authorization) {
        final String sql = """
            INSERT INTO authorization_codes(
                value,
                challenge,
                expired_at,
                access_type_name,
                grant_type_name,
                access_token_core_key,
                id_token_core_key,
                refresh_token_core_key
            )
            VALUES(
                :value,
                :challenge,
                :expired_at,
                :access_type_name,
                :grant_type_name,
                :access_token_core_key,
                :id_token_core_key,
                :refresh_token_core_key
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("value", authorization.getAuthorizationCode().getValue().getValue());
        params.put("challenge", authorization.getAuthorizationCode().getChallenge().getValue());
        params.put("expired_at", authorization.getAuthorizationCode().getExpiredAt().getValue());
        params.put("access_type_name", authorization.getAccessType().getValue());
        params.put("grant_type_name", authorization.getGrantType().getValue());
        params.put("access_token_core_key", UUID.fromString(authorization.getLinkedAccessTokenCoreKey().getValue()));
        params.put("id_token_core_key", UUID.fromString(authorization.getLinkedIdTokenCoreKey().getValue()));
        params.put("refresh_token_core_key", UUID.fromString(authorization.getLinkedRefreshTokenCoreKey().getValue()));

        jdbcTemplate.update(sql, params); 
    }
}