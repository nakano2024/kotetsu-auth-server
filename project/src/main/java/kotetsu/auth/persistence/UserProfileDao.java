package kotetsu.auth.persistence;

import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.UserProfileData;
import kotetsu.auth.application.persistence.IFindUserProfileByEmailPort;

@Component
public class UserProfileDao implements IFindUserProfileByEmailPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProfileDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserProfileData findByEmail(String email) {
        final Map<String, String> parameters = Map.of("email", email);

        try {
            final Map<String, Object> userRecord = jdbcTemplate.queryForMap("""
                SELECT users.code, users.name, users.email, files.url as image_url
                FROM users
                JOIN files ON users.image_file_code = files.code
                WHERE users.email = :email;
            """, parameters);

            return UserProfileData.of(
                (UUID) userRecord.get("code"),
                (String) userRecord.get("name"),
                (String) userRecord.get("email"),
                (String) userRecord.get("image_url")
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
