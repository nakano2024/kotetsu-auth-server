package kotetsu.auth.feature.idtokendraftdao;

import java.sql.SQLException;
import java.util.HashMap;
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

import kotetsu.auth.application.dto.store.IdTokenDraftStore;
import kotetsu.auth.persistence.IdTokenDraftDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StoreTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    IdTokenDraftDao idTokenDraftDao;

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

        // Client informations
        Map<String, Object> clientParameters = new HashMap<>();
        clientParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        clientParameters.put("name", "test-client");
        clientParameters.put("secret", "test-secret");
        clientParameters.put("redirect_uri", "https://example.com/callback");
        clientParameters.put("is_valid", true);
        jdbcTemplate.update("""
            INSERT INTO client_informations(code, name, secret, redirect_uri, is_valid)
            VALUES(:code, :name, :secret, :redirect_uri, :is_valid)
        """, clientParameters);
    }

    @Test
    public void canStoreIdTokenDraft() throws SQLException {
        IdTokenDraftStore idTokenDraft = IdTokenDraftStore.of(
            UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"),
            "https://auth.example.com",
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            "test-nonce-value"
        );

        UUID idTokenDraftCode = idTokenDraftDao.store(idTokenDraft);

        assertNotNull(idTokenDraftCode);

        // Verify draft was stored
        Map<String, Object> params = new HashMap<>();
        params.put("code", idTokenDraftCode);
        
        Map<String, Object> result = jdbcTemplate.queryForMap(
            "SELECT code, issuer, subject, audience, nonce FROM id_token_drafts WHERE code = :code",
            params
        );
        
        assertNotNull(result);
        assertEquals(idTokenDraftCode, result.get("code"));
        assertEquals("https://auth.example.com", result.get("issuer"));
        assertEquals(UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"), result.get("subject"));
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), result.get("audience"));
        assertEquals("test-nonce-value", result.get("nonce"));
    }
}