package kotetsu.auth.feature.refreshtokendao;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
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

import kotetsu.auth.application.dto.store.RefreshTokenStore;
import kotetsu.auth.persistence.RefreshTokenDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StoreTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    RefreshTokenDao refreshTokenDao;

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

        // Access token draft
        Map<String, Object> accessTokenDraftParameters = new HashMap<>();
        accessTokenDraftParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        accessTokenDraftParameters.put("issuer", "https://auth.example.com");
        accessTokenDraftParameters.put("subject", UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"));
        jdbcTemplate.update("""
            INSERT INTO access_token_drafts(code, issuer, subject)
            VALUES(:code, :issuer, :subject)
        """, accessTokenDraftParameters);

        // ID token draft
        Map<String, Object> idTokenDraftParameters = new HashMap<>();
        idTokenDraftParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        idTokenDraftParameters.put("issuer", "https://auth.example.com");
        idTokenDraftParameters.put("subject", UUID.fromString("9afd6f24-49b8-0ddd-1797-552b9b31dbe4"));
        idTokenDraftParameters.put("audience", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        idTokenDraftParameters.put("nonce", "test-nonce-value");
        jdbcTemplate.update("""
            INSERT INTO id_token_drafts(code, issuer, subject, audience, nonce)
            VALUES(:code, :issuer, :subject, :audience, :nonce)
        """, idTokenDraftParameters);
    }

    @Test
    public void canStoreRefreshToken() throws SQLException {
        Instant fixedInstant = Instant.parse("2023-11-14T00:00:00Z");
        
        RefreshTokenStore refreshToken = RefreshTokenStore.of(
            "test-refresh-token-value",
            UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
            UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
            Date.from(fixedInstant),
            Date.from(fixedInstant.plusSeconds(2592000)) // 30 days later
        );

        String storedValue = refreshTokenDao.store(refreshToken);

        assertNotNull(storedValue);
        assertEquals("test-refresh-token-value", storedValue);

        // Verify refresh token was stored
        Map<String, Object> params = new HashMap<>();
        params.put("value", "test-refresh-token-value");
        
        Map<String, Object> result = jdbcTemplate.queryForMap("""
            SELECT value, access_token_draft_code, id_token_draft_code, issued_at, expired_at
            FROM refresh_tokens
            WHERE value = :value
        """, params);
        
        assertNotNull(result);
        assertEquals("test-refresh-token-value", result.get("value"));
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"), result.get("access_token_draft_code"));
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"), result.get("id_token_draft_code"));
        assertEquals(Date.from(fixedInstant), result.get("issued_at"));
        assertEquals(Date.from(fixedInstant.plusSeconds(2592000)), result.get("expired_at"));
    }
}