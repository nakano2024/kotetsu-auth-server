package kotetsu.auth.persistence;

import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.IdTokenDraftData;
import kotetsu.auth.application.dto.store.IdTokenDraftStore;
import kotetsu.auth.application.persistence.IFindIdTokenDraftByCodePort;
import kotetsu.auth.application.persistence.IStoreIdTokenDraftPort;

@Component
public class IdTokenDraftDao implements IFindIdTokenDraftByCodePort, IStoreIdTokenDraftPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public IdTokenDraftDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public IdTokenDraftData findByCode(final UUID id) {
        // TODO: Implement database query logic
        return null;
    }

    @Override
    public UUID store(IdTokenDraftStore idToken) {
        // TODO: Implement database storage logic
        return null;
    }
}
