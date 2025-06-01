package kotetsu.auth.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.application.persistence.IFindUserCredentialByEmailPort;

@Component
public class UserCredentialDao implements IFindUserCredentialByEmailPort {
    final NamedParameterJdbcTemplate jdbcTemplate;

    public UserCredentialDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserCredentialData findByEmail(String email) {
        final String sql = """
            SELECT code, email, password FROM users WHERE email = :email
        """;

        Map<String, String> params = new HashMap<>();
        params.put("email", email);

        try {
            Map<String, Object> userRecord = jdbcTemplate.queryForMap(sql, params);

            return UserCredentialData.of(
                (String) userRecord.get("email"),
                (String) userRecord.get("password")
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
