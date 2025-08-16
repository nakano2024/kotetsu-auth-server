package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import kotetsu.auth.application.domain.entity.MeProfile;
import kotetsu.auth.application.domain.repository.IFetchMeProfilePort;
import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.UserName;

public class MeProfileRepository implements IFetchMeProfilePort {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public MeProfileRepository(final NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<MeProfile> fetch(Key key) {
        final String sql = """
            SELECT u.key, u.name, u.email, f.url as f_url
            FROM users AS u
            JOIN user_image_files AS uif ON u.key = uif.user_key
            JOIN files AS f ON uif.file_key = f.key
            WHERE u.key = :key
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", key.getValue());
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(MeProfile.of(
            Key.of(String.valueOf(row.get("key"))),
            UserName.of(String.valueOf(row.get("name"))),
            Email.of(String.valueOf(row.get("email"))), 
            ImageUrl.of(String.valueOf(row.get("f_url")))
        ));
    }
}