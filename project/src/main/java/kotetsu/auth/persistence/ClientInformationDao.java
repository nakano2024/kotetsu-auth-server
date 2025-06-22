package kotetsu.auth.persistence;

import java.util.UUID;

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
        // TODO: Implement database query logic
        return null;
    }

    @Override
    public ClientInformationData findByIdAndSecret(final String clientId, final String clientSecret) {
        // TODO: Implement database query logic
        return null;
    }

    @Override
    public ClientInformationData findById(String id) {
        // TODO: Implement database query logic
        return null;
    }
}
