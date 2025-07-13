package kotetsu.auth.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.persistence.IFIndClientInformationByCodePort;
import kotetsu.auth.application.persistence.IFindClientInformationByIdAndSecretPort;
import kotetsu.auth.application.persistence.IFindClientInformationByIdPort;

@Component
public class ClientInformationDao implements IFIndClientInformationByCodePort, IFindClientInformationByIdAndSecretPort, IFindClientInformationByIdPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClientInformationDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ClientInformationData findByCode(UUID code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap("""
                SELECT code, name, secret, redirect_uri, is_valid 
                FROM client_informations 
                WHERE code = :code
            """, params);

            return ClientInformationData.of(
                (UUID) result.get("code"),
                (String) result.get("name"),
                (String) result.get("secret"),
                (String) result.get("redirect_uri"),
                (Boolean) result.get("is_valid")
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ClientInformationData findByIdAndSecret(final String clientId, final String clientSecret) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap("""
                SELECT code, name, secret, redirect_uri, is_valid 
                FROM client_informations 
                WHERE name = :client_id AND secret = :client_secret AND is_valid = true
            """, params);

            return ClientInformationData.of(
                (UUID) result.get("code"),
                (String) result.get("name"),
                (String) result.get("secret"),
                (String) result.get("redirect_uri"),
                (Boolean) result.get("is_valid")
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ClientInformationData findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap("""
                SELECT code, name, secret, redirect_uri, is_valid 
                FROM client_informations 
                WHERE name = :id AND is_valid = true
            """, params);

            return ClientInformationData.of(
                (UUID) result.get("code"),
                (String) result.get("name"),
                (String) result.get("secret"),
                (String) result.get("redirect_uri"),
                (Boolean) result.get("is_valid")
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
