package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.ExistingIdTokenCore;
import kotetsu.auth.application.domain.repository.IFetchExistingIdTokenCorePort;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import kotetsu.auth.application.domain.value.UserName;

@Component
public class ExistingIdTokenCoreRepository implements IFetchExistingIdTokenCorePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExistingIdTokenCoreRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ExistingIdTokenCore> fetch(final Key key) {
        final String sql = """
            SELECT itc.key, itc.issuer, itc.subject, itc.nonce, u.name AS u_name, u.email AS u_email, f.url AS f_url
            FROM id_token_cores AS itc
            JOIN users AS u ON itc.subject = u.key
            JOIN user_image_files AS uif ON u.key = uif.user_key
            JOIN files AS f ON uif.file_key = f.key
            WHERE itc.key = :key;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(key.getValue()));
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ExistingIdTokenCore.of(
            Key.of(String.valueOf(row.get("key"))),
            Issuer.of((String) row.get("issuer")),
            Subject.of(String.valueOf(row.get("subject"))),
            Nonce.of((String) row.get("nonce")),
            IdTokenProfile.of(
                UserName.of((String) row.get("u_name")),
                Email.of((String) row.get("u_email")),
                ImageUrl.of((String) row.get("f_url"))
            )
        ));
    }
}