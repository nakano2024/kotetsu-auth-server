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
            SELECT key, email, password_hash
            FROM users
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
            String.valueOf(row.get("client_id")),
            String.valueOf(row.get("email")),
            String.valueOf(row.get("password_hash"))
        ));
    }
}
