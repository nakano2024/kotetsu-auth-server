package kotetsu.auth.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.application.query.IFindUserCredentialByEmailPort;

@Component
public class UserCredentialQueryService implements IFindUserCredentialByEmailPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserCredentialQueryService(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<UserCredentialData> findByEmail(String email) {
        final String sql = """
            SELECT u.key AS u_key, name AS u_name, f.url AS f_url, email AS u_email, is_active as u_is_active, u.password_hash AS u_password_hash
            FROM users as u
            JOIN user_image_files AS uif ON u.key = uif.user_key
            JOIN files AS f ON uif.file_key = f.key
            WHERE email = :email;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(UserCredentialData.of(
            String.valueOf(row.get("u_key")),
            String.valueOf(row.get("u_name")),
            String.valueOf(row.get("f_url")),
            String.valueOf(row.get("u_email")),
            String.valueOf(row.get("u_password_hash")),
            (boolean) row.get("u_is_active")
        ));
    }
}
