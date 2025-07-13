package kotetsu.auth.feature.scopedao;

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

import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.persistence.ScopeDao;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FindByClientCodeTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    ScopeDao scopeDao;

    @BeforeEach
    @Transactional
    public void setUp() throws SQLException {
        // Resource server
        Map<String, Object> resourceServerParameters = new HashMap<>();
        resourceServerParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        resourceServerParameters.put("name", "Test ResourceServer");
        resourceServerParameters.put("url", "https://api.test.example.com");
        jdbcTemplate.update("""
            INSERT INTO resource_servers(code, name, url)
            VALUES(:code, :name, :url)
        """, resourceServerParameters);

        // Client information
        Map<String, Object> clientParameters = new HashMap<>();
        clientParameters.put("code", UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        clientParameters.put("name", "test-client");
        clientParameters.put("secret", "test-secret");
        clientParameters.put("redirect_uri", "https://example.com/callback");
        clientParameters.put("is_valid", true);
        jdbcTemplate.update("""
            INSERT INTO client_informations(code, name, secret, redirect_uri, is_valid)
            VALUES(:code, :name, :secret, :redirect_uri, :is_valid)
        """, clientParameters);

        // Scopes
        Map<String, Object> scope1Parameters = new HashMap<>();
        scope1Parameters.put("code", UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"));
        scope1Parameters.put("resource_server_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        scope1Parameters.put("name", "test.read");
        jdbcTemplate.update("""
            INSERT INTO scopes(code, resource_server_code, name)
            VALUES(:code, :resource_server_code, :name)
        """, scope1Parameters);

        Map<String, Object> scope2Parameters = new HashMap<>();
        scope2Parameters.put("code", UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"));
        scope2Parameters.put("resource_server_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        scope2Parameters.put("name", "test.write");
        jdbcTemplate.update("""
            INSERT INTO scopes(code, resource_server_code, name)
            VALUES(:code, :resource_server_code, :name)
        """, scope2Parameters);

        // Client permitted scopes
        Map<String, Object> permission1Parameters = new HashMap<>();
        permission1Parameters.put("client_information_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        permission1Parameters.put("scope_code", UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"));
        jdbcTemplate.update("""
            INSERT INTO client_permitted_scopes(client_information_code, scope_code)
            VALUES(:client_information_code, :scope_code)
        """, permission1Parameters);

        Map<String, Object> permission2Parameters = new HashMap<>();
        permission2Parameters.put("client_information_code", UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        permission2Parameters.put("scope_code", UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"));
        jdbcTemplate.update("""
            INSERT INTO client_permitted_scopes(client_information_code, scope_code)
            VALUES(:client_information_code, :scope_code)
        """, permission2Parameters);
    }

    @Test
    public void canFetchIfDataExist() throws SQLException {
        List<ScopeData> scopes = scopeDao.findByClientCode(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        
        assertNotNull(scopes);
        assertEquals(2, scopes.size());
        
        ScopeData scope1 = scopes.get(0);
        assertEquals(UUID.fromString("66d0d3ec-b80a-86fe-9958-fae53dc740b3"), scope1.getCode());
        assertEquals("test.read", scope1.getName());
        
        ScopeData scope2 = scopes.get(1);
        assertEquals(UUID.fromString("d2df8394-9373-492b-80bc-60c2739b5cb9"), scope2.getCode());
        assertEquals("test.write", scope2.getName());
    }

    @Test
    public void returnEmptyListIfDataDoseNotExist() throws SQLException {
        List<ScopeData> scopes = scopeDao.findByClientCode(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"));
        assertNotNull(scopes);
        assertEquals(0, scopes.size());
    }
}