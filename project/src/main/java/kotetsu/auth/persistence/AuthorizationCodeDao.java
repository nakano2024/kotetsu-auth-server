package kotetsu.auth.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.AuthorizationCodeData;
import kotetsu.auth.application.dto.store.AuthorizationCodeStore;
import kotetsu.auth.application.persistence.IDeleteAuthorizationCodePort;
import kotetsu.auth.application.persistence.IFindAuthorizationCodeByCodePort;
import kotetsu.auth.application.persistence.IStoreAuthorizationCodePort;

@Component
public class AuthorizationCodeDao implements IDeleteAuthorizationCodePort, IFindAuthorizationCodeByCodePort, IStoreAuthorizationCodePort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorizationCodeDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteByCode(final String code) {
        // TODO: Implement database deletion logic
    }

    @Override
    public AuthorizationCodeData findByCode(final String code) {
        // TODO: Implement database query logic
        return null;
    }

    @Override
    public String store(AuthorizationCodeStore authorizationCode) {
        // TODO: Implement database storage logic
        return null;
    }
}
