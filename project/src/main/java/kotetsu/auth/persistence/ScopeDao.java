package kotetsu.auth.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ScopeData;
import kotetsu.auth.application.persistence.IFindPermittedScopeListByClientCodePort;
import kotetsu.auth.application.persistence.IFindScopeListByScopeNameListPort;

@Component
public class ScopeDao implements
    IFindPermittedScopeListByClientCodePort,
    IFindScopeListByScopeNameListPort
{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScopeDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ScopeData> findByClientCode(UUID clientCode) {
        // TODO: Implement database query logic
        return null;
    }

    @Override
    public List<ScopeData> findByScopeNames(List<String> scopeNames) {
        // TODO: Implement database query logic
        return null;
    }
}
