package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.PendingIdTokenCore;
import kotetsu.auth.application.domain.repository.IStorePendingIdTokenCorePort;

@Component
public class PendingIdTokenCoreRepository implements IStorePendingIdTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PendingIdTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void store(PendingIdTokenCore idTokenCore) {
        final String sql = """
            INSERT INTO id_token_cores(
                key,
                issuer,
                audience,
                subject,
                nonce
            )
            VALUES(
                :key,
                :issuer,
                :audience,
                :subject,
                :nonce
            );
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(idTokenCore.getKey().getValue()));
        params.put("issuer", idTokenCore.getIssuer().getValue());
        params.put("audience", idTokenCore.getAudience().getValue());
        params.put("subject", UUID.fromString(idTokenCore.getSubject().getValue()));
        params.put("nonce", idTokenCore.getNonce().getValue());

        jdbcTemplate.update(sql, params);
    }
}