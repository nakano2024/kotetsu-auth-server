package kotetsu.auth.feature.clientinformationdao;

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

import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.persistence.ClientInformationDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindByIdTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    ClientInformationDao clientInformationDao;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        parameters.put("name", "test-client");
        parameters.put("secret", "test-secret");
        parameters.put("redirect_uri", "https://example.com/callback");
        parameters.put("is_valid", true);
        jdbcTemplate.update(
            "INSERT INTO client_informations(code, name, secret, redirect_uri, is_valid) VALUES (:code, :name, :secret, :redirect_uri, :is_valid)",
            parameters
        );
    }

    @Test
    public void canFetchIfDataExist() throws SQLException {
        ClientInformationData clientInformation = clientInformationDao.findById("test-client");
        
        assertNotNull(clientInformation);
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), clientInformation.getCode());
        assertEquals("test-client", clientInformation.getName());
        assertEquals("test-secret", clientInformation.getSecret());
        assertEquals("https://example.com/callback", clientInformation.getRedirectUri());
        assertEquals(true, clientInformation.isValid());
    }

    @Test
    public void returnNullIfDataDoseNotExist() throws SQLException {
        ClientInformationData clientInformation = clientInformationDao.findById("nonexistent-client");
        assertNull(clientInformation);
    }
}