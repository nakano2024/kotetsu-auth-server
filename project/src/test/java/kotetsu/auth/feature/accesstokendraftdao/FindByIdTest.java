package kotetsu.auth.feature.accesstokendraftdao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import kotetsu.auth.application.dto.data.AccessTokenDraftData;
import kotetsu.auth.persistence.AccessTokenDraftDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindByIdTest {

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

        // Access token draft
        Map<String, Object> draftParameters = new HashMap<>();
        draftParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        draftParameters.put("issuer", "https://auth.example.com");
        draftParameters.put("subject", UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"));
        jdbcTemplate.update("""
            INSERT INTO access_token_drafts(code, issuer, subject)
            VALUES(:code, :issuer, :subject)
        """, draftParameters);

        // Access token draft scopes
        Map<String, Object> draftScopeParameters = new HashMap<>();
        draftScopeParameters.put("access_token_draft_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        draftScopeParameters.put("scope_code", UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"));
        jdbcTemplate.update("""
            INSERT INTO access_token_draft_scopes(access_token_draft_code, scope_code)
            VALUES(:access_token_draft_code, :scope_code)
        """, draftScopeParameters);
    }

    @Test
    public void canFetchIfDataExist() throws SQLException {
        AccessTokenDraftData accessTokenDraft = accessTokenDraftDao.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        
        assertNotNull(accessTokenDraft);
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), accessTokenDraft.getCode());
        assertEquals("https://auth.example.com", accessTokenDraft.getIssuer());
        assertEquals(UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"), accessTokenDraft.getSubject());
        
        assertNotNull(accessTokenDraft.getScopes());
        assertEquals(1, accessTokenDraft.getScopes().size());
        assertEquals(UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"), accessTokenDraft.getScopes().get(0).getCode());
        assertEquals("test.readonly", accessTokenDraft.getScopes().get(0).getName());
        
        assertNotNull(accessTokenDraft.getAudiences());
        assertEquals(1, accessTokenDraft.getAudiences().size());
        assertEquals(UUID.fromString("5ed8a011-a486-bc8a-728c-6f55375df39c"), accessTokenDraft.getAudiences().get(0).getCode());
        assertEquals("Test ResourceServer", accessTokenDraft.getAudiences().get(0).getName());
        assertEquals("https://api.test.example.com", accessTokenDraft.getAudiences().get(0).getUrl());
    }

    @Test
    public void returnNullIfDataDoseNotExist() throws SQLException {
        AccessTokenDraftData accessTokenDraft = accessTokenDraftDao.findById(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        assertNull(accessTokenDraft);
    }
}