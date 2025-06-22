package kotetsu.auth.persistence;

import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.AccessTokenDraftData;
import kotetsu.auth.application.dto.store.AccessTokenDraftStore;
import kotetsu.auth.application.persistence.IFindAccessTokenDraftByIdPort;
import kotetsu.auth.application.persistence.IStoreAccessTokenDraftPort;

@Component
public class AccessTokenDraftDao implements IFindAccessTokenDraftByIdPort, IStoreAccessTokenDraftPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccessTokenDraftDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AccessTokenDraftData findById(final UUID id) {
        // TODO: Implement database query logic
        return null;
    }

    @Override
    public UUID store(AccessTokenDraftStore accessTokenDraft) {
        // TODO: Implement database storage logic
        return null;
    }
}
