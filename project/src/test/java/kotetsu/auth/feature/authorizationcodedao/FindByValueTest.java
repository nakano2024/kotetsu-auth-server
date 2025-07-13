package kotetsu.auth.feature.authorizationcodedao;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
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

import kotetsu.auth.application.dto.data.AuthorizationCodeData;
import kotetsu.auth.persistence.AuthorizationCodeDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindByValueTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    AuthorizationCodeDao authorizationCodeDao;

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

        // Authorization code
        Instant fixedInstant = Instant.parse("2023-11-14T00:00:00Z");
        Map<String, Object> authCodeParameters = new HashMap<>();
        authCodeParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440003"));
        authCodeParameters.put("value", "test-authorization-code-value");
        authCodeParameters.put("challenge", "test-challenge-value");
        authCodeParameters.put("access_token_draft_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        authCodeParameters.put("id_token_draft_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        authCodeParameters.put("enable_openid", true);
        authCodeParameters.put("enable_offline_access", false);
        authCodeParameters.put("issued_at", Date.from(fixedInstant));
        authCodeParameters.put("expired_at", Date.from(fixedInstant.plusSeconds(600)));
        jdbcTemplate.update("""
            INSERT INTO authorization_codes(code, value, challenge, access_token_draft_code, id_token_draft_code, enable_openid, enable_offline_access, issued_at, expired_at)
            VALUES(:code, :value, :challenge, :access_token_draft_code, :id_token_draft_code, :enable_openid, :enable_offline_access, :issued_at, :expired_at)
        """, authCodeParameters);
    }

    @Test
    public void canFetchIfDataExist() throws SQLException {
        AuthorizationCodeData authorizationCode = authorizationCodeDao.findByValue("test-authorization-code-value");
        
        assertNotNull(authorizationCode);
        assertEquals("test-authorization-code-value", authorizationCode.getValue());
        assertEquals("test-challenge-value", authorizationCode.getChallenge());
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"), authorizationCode.getAccessTokenDraftCode());
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"), authorizationCode.getIdTokenDraftCode());
        assertEquals(true, authorizationCode.isEnableOpenid());
        assertEquals(false, authorizationCode.isEnableOfflineAccess());
        assertEquals(Date.from(Instant.parse("2023-11-14T00:00:00Z")), authorizationCode.getIssuedAt());
        assertEquals(Date.from(Instant.parse("2023-11-14T00:10:00Z")), authorizationCode.getExpiredAt());
    }

    @Test
    public void returnNullIfDataDoseNotExist() throws SQLException {
        AuthorizationCodeData authorizationCode = authorizationCodeDao.findByValue("nonexistent-authorization-code");
        assertNull(authorizationCode);
    }
}