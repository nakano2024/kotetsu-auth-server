package kotetsu.auth.feature.accesstokendraftdao;

import java.sql.SQLException;
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

import kotetsu.auth.application.dto.store.AccessTokenDraftStore;
import kotetsu.auth.persistence.AccessTokenDraftDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StoreTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    AccessTokenDraftDao accessTokenDraftDao;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {
        // Files
        Map<String, Object> fileParameters = new HashMap<>();
        fileParameters.put("code", UUID.fromString("f5a30b28-a6bf-e194-272f-812295dd6d32"));
        fileParameters.put("name", "0ef2bc81-1804-6d0b-d0ba-2e31ae44a3cb.png");
        fileParameters.put("url", "https://example.com/0ef2bc81-1804-6d0b-d0ba-2e31ae44a3cb.png");
        jdbcTemplate.update("INSERT INTO files(code, name, url) VALUES (:code, :name, :url)", fileParameters);

        // Users
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

        // Resource servers
        Map<String, Object> resourceServerParameters = new HashMap<>();
        resourceServerParameters.put("code", UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"));
        resourceServerParameters.put("name", "Test ResourceServer");
        resourceServerParameters.put("url", "https://api.test.example.com");
        jdbcTemplate.update("""
            INSERT INTO resource_servers(code, name, url)
            VALUES(:code, :name, :url)
        """, resourceServerParameters);

        // Scopes
        Map<String, Object> scope1Parameters = new HashMap<>();
        scope1Parameters.put("code", UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"));
        scope1Parameters.put("resource_server_code", UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"));
        scope1Parameters.put("name", "test.readonly");
        jdbcTemplate.update("""
            INSERT INTO scopes(code, resource_server_code, name)
            VALUES(:code, :resource_server_code, :name)
        """, scope1Parameters);

        Map<String, Object> scope2Parameters = new HashMap<>();
        scope2Parameters.put("code", UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"));
        scope2Parameters.put("resource_server_code", UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"));
        scope2Parameters.put("name", "test.write");
        jdbcTemplate.update("""
            INSERT INTO scopes(code, resource_server_code, name)
            VALUES(:code, :resource_server_code, :name)
        """, scope2Parameters);
    }

    @Test
    public void canStoreAccessTokenDraft() throws SQLException {
        AccessTokenDraftStore accessTokenDraft = AccessTokenDraftStore.of(
            "https://auth.example.com",
            UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"),
            List.of(
                UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"),
                UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9")
            )
        );

        UUID accessTokenDraftCode = accessTokenDraftDao.store(accessTokenDraft);

        assertNotNull(accessTokenDraftCode);

        // Verify draft was stored
        Map<String, Object> params = new HashMap<>();
        params.put("code", accessTokenDraftCode);
        
        Map<String, Object> result = jdbcTemplate.queryForMap(
            "SELECT code, issuer, subject FROM access_token_drafts WHERE code = :code",
            params
        );
        
        assertNotNull(result);
        assertEquals(accessTokenDraftCode, result.get("code"));
        assertEquals("https://auth.example.com", result.get("issuer"));
        assertEquals(UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"), result.get("subject"));

        // Verify scopes were stored
        List<Map<String, Object>> scopeResults = jdbcTemplate.queryForList(
            "SELECT scope_code FROM access_token_draft_scopes WHERE access_token_draft_code = :access_token_draft_code ORDER BY scope_code",
            Map.of("access_token_draft_code", accessTokenDraftCode)
        );

        assertEquals(2, scopeResults.size());
        assertEquals(UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"), scopeResults.get(0).get("scope_code"));
        assertEquals(UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"), scopeResults.get(1).get("scope_code"));
    }
}