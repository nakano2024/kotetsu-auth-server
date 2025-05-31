package kotetsu.auth.repository;

import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.UserProfile;
import kotetsu.auth.application.domain.repository.IFetchUserProfileByEmailRepository;
import kotetsu.auth.application.domain.value.Code;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.UserImageUrl;
import kotetsu.auth.application.domain.value.UserName;

@Component
public class UserProfileRepository implements IFetchUserProfileByEmailRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserProfileRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserProfile fetchByEmail(Email email) {
        final String sql = """
            SELECT users.code, users.name, users.email, files.url as image_url
             FROM users
             JOIN files ON users.image_file_code = files.code
             WHERE users.email = :email;
        """;

        final Map<String, String> parameters = Map.of("email", email.getValue());

        try {
            final Map<String, Object> userRecord = jdbcTemplate.queryForMap(sql, parameters);
            return UserProfile.fetch(
                Code.of(userRecord.get("code").toString()),
                UserName.of((String) userRecord.get("name")),
                Email.of((String) userRecord.get("email")),
                UserImageUrl.of((String) userRecord.get("image_url"))
            );
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
