package kotetsu.auth.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kotetsu.auth.application.domain.entity.ResourceOwnerValidator;
import kotetsu.auth.application.domain.repository.IFetchResourceOwnerValidator;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.UserActivation;

public class ResourceOwnerValidatorRepository
    implements IFetchResourceOwnerValidator
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ResourceOwnerValidatorRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ResourceOwnerValidator> fetch(Key key) {
        final String sql = """
            SELECT is_active
            FROM users
            WHERE key = :key;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("key", UUID.fromString(key.getValue()));
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ResourceOwnerValidator.of(
            UserActivation.of((boolean) row.get("is_active"))
        ));
    }
}
