package kotetsu.auth.feature.accesstokendao;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.store.AccessTokenStore;
import kotetsu.auth.persistence.AccessTokenDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StoreTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    AccessTokenDao accessTokenDao;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", UUID.fromString("f5a30b28-a6bf-e194-272f-812295dd6d32"));
        parameters.put("name", "0ef2bc81-1804-6d0b-d0ba-2e31ae44a3cb.png");
        parameters.put("url", "https://example.com/0ef2bc81-1804-6d0b-d0ba-2e31ae44a3cb.png");
        jdbcTemplate.update("INSERT INTO files(code, name, url) VALUES (:code, :name, :url)", parameters);

        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put("code", UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"));
        userParameters.put("name", "田中太郎");
        userParameters.put("email", "tanaka@example.com");
        userParameters.put("password", "$2a$08$I9vocqeWlWqAA/mAux33O.2v2smtFpVf8GdTyJt8rVe45pjwR8Q4S");
        userParameters.put("image_file_code", UUID.fromString("f5a30b28-a6bf-e194-272f-812295dd6d32"));
        jdbcTemplate.update(
            "INSERT INTO users(code, name, email, password, image_file_code) VALUES (:code, :name, :email, :password, :image_file_code)", 
            userParameters
        );

        Map<String, Object> resourceServerPatameters = new HashMap<>();
        resourceServerPatameters.put("code", UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"));
        resourceServerPatameters.put("name", "Test ResourceServer");
        resourceServerPatameters.put("url", "https://api.test.example.com");
        jdbcTemplate.update("""
            INSERT INTO resource_servers(code, name, url)
             VALUES(:code, :name, :url);
        """, resourceServerPatameters);

        Map<String, Object> testReadonlyScopePatameters = new HashMap<>();
        testReadonlyScopePatameters.put("code", UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"));
        testReadonlyScopePatameters.put("resource_server_code", UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"));
        testReadonlyScopePatameters.put("name", "test.readonly");
        jdbcTemplate.update("""
            INSERT INTO scopes(code, resource_server_code, name)
             VALUES(:code, :resource_server_code, :name);
        """, testReadonlyScopePatameters);

        Map<String, Object> testWriteScopePatameters = new HashMap<>();
        testWriteScopePatameters.put("code", UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"));
        testWriteScopePatameters.put("resource_server_code", UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"));
        testWriteScopePatameters.put("name", "test.write");
        jdbcTemplate.update("""
            INSERT INTO scopes(code, resource_server_code, name)
             VALUES(:code, :resource_server_code, :name);
        """, testWriteScopePatameters);
    }

    @Test
    public void canStoreAccessToken() throws SQLException {
        Instant fixedInstant = Instant.parse("2023-11-14T00:00:00Z");
        
        final List<UUID> scopeCodes = List.of(
            UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"),
            UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9")
        );

        AccessTokenStore accessToken = AccessTokenStore.of(
            "test-access-token-value",
            "https://auth.example.com",
            UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"),
            scopeCodes,
            Date.from(fixedInstant),
            Date.from(fixedInstant.plusSeconds(3600))
        );

        accessTokenDao.store(accessToken);

        Map<String, Object> params = new HashMap<>();
        params.put("value", "test-access-token-value");
        
        Map<String, Object> result = jdbcTemplate.queryForMap(
            "SELECT value, issuer, subject, issued_at, expired_at FROM access_tokens WHERE value = :value",
            params
        );
        
        assertNotNull(result);
        assertEquals("test-access-token-value", result.get("value"));
        assertEquals("https://auth.example.com", result.get("issuer"));
        assertEquals(UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"), result.get("subject"));
        assertEquals(Date.from(fixedInstant), result.get("issued_at"));
        assertEquals(Date.from(fixedInstant.plusSeconds(3600)), result.get("expired_at"));

        // スコープがaccess_token_scopesテーブルに保存されているかを確認
        Map<String, Object> accessTokenCodeParams = new HashMap<>();
        accessTokenCodeParams.put("value", "test-access-token-value");
        
        UUID accessTokenCode = jdbcTemplate.queryForObject(
            "SELECT code FROM access_tokens WHERE value = :value",
            accessTokenCodeParams,
            UUID.class
        );

        List<Map<String, Object>> scopeResults = jdbcTemplate.queryForList(
            "SELECT scope_code FROM access_token_scopes WHERE access_token_code = :access_token_code ORDER BY scope_code",
            Map.of("access_token_code", accessTokenCode)
        );

        assertEquals(2, scopeResults.size());
        assertEquals(UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"), scopeResults.get(0).get("scope_code"));
        assertEquals(UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"), scopeResults.get(1).get("scope_code"));
    }
}