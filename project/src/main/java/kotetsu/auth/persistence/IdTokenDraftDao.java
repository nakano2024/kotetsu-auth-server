package kotetsu.auth.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.IdTokenDraftData;
import kotetsu.auth.application.dto.data.IdTokenProfileData;
import kotetsu.auth.application.dto.store.IdTokenDraftStore;
import kotetsu.auth.application.persistence.IFindIdTokenDraftByCodePort;
import kotetsu.auth.application.persistence.IStoreIdTokenDraftPort;

@Component
public class IdTokenDraftDao implements IFindIdTokenDraftByCodePort, IStoreIdTokenDraftPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IdTokenDraftDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public IdTokenDraftData findByCode(final UUID code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap("""
                SELECT itd.code, itd.issuer, itd.subject, itd.audience, itd.nonce, u.name as user_name, u.email as user_email, f.url as user_image_url
                FROM id_token_drafts itd
                INNER JOIN users u ON itd.subject = u.code
                INNER JOIN files f ON u.image_file_code = f.code
                WHERE itd.code = :code
            """, params);

            IdTokenProfileData profile = IdTokenProfileData.of(
                (String) result.get("user_name"),
                (String) result.get("user_email"),
                (String) result.get("user_image_url")
            );

            return IdTokenDraftData.of(
                (UUID) result.get("code"),
                (UUID) result.get("subject"),
                (UUID) result.get("audience"),
                profile,
                (String) result.get("nonce")
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UUID store(IdTokenDraftStore idToken) {
        final UUID idTokenDraftCode = UUID.randomUUID();

        final Map<String, Object> params = new HashMap<>();
        params.put("code", idTokenDraftCode);
        params.put("issuer", idToken.getIssuer());
        params.put("subject", idToken.getSubject());
        params.put("audience", idToken.getAudience());
        params.put("nonce", idToken.getNonce());

        jdbcTemplate.update("""
            INSERT INTO id_token_drafts(code, issuer, subject, audience, nonce)
            VALUES(:code, :issuer, :subject, :audience, :nonce)
        """, params);

        return idTokenDraftCode;
    }
}
