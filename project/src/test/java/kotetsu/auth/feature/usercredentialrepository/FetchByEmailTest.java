package kotetsu.auth.feature.usercredentialrepository;

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

import kotetsu.auth.application.dto.data.UserCredentialData;
import kotetsu.auth.persistence.UserCredentialDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FetchByEmailTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    UserCredentialDao userCredentialDao;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", UUID.fromString("f5a30b28-a6bf-e194-272f-812295dd6d32"));
        parameters.put("name", "0ef2bc81-1804-6d0b-d0ba-2e31ae44a3cb.png");
        parameters.put("url", "https://example.com/0ef2bc81-1804-6d0b-d0ba-2e31ae44a3cb.png");
        jdbcTemplate.update("INSERT INTO files(code, name, url) VALUES (:code, :name, :url)", parameters);
    }

    @Test
    public void canFetchIfDataExist() throws SQLException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", UUID.fromString( "9afd6f24-49b8-0ddd-1797-552b9b31dbe4"));
        parameters.put("name", "田中太郎");
        parameters.put("email", "tanaka@example.com");
        parameters.put("password", "$2a$08$I9vocqeWlWqAA/mAux33O.2v2smtFpVf8GdTyJt8rVe45pjwR8Q4S");
        parameters.put("image_file_code", UUID.fromString("f5a30b28-a6bf-e194-272f-812295dd6d32"));
        jdbcTemplate.update(
            "INSERT INTO users(code, name, email, password, image_file_code) VALUES (:code, :name, :email, :password, :image_file_code)", 
            parameters
        );

        UserCredentialData userCredential = userCredentialDao.findByEmail("tanaka@example.com");
        assertNotNull(userCredential);
        assertEquals("tanaka@example.com", userCredential.getEmail());
        assertEquals("$2a$08$I9vocqeWlWqAA/mAux33O.2v2smtFpVf8GdTyJt8rVe45pjwR8Q4S", userCredential.getHashedPassword());
    }

    @Test
    public void returnNullIfDataDoseNotExist() throws SQLException {
        UserCredentialData userCredential = userCredentialDao.findByEmail("tanaka@example.com");
        assertNull(userCredential);
    }
}
