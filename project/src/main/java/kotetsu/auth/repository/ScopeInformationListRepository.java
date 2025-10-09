package kotetsu.auth.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.domain.entity.ScopeInformation;
import kotetsu.auth.application.domain.entity.ScopeInformationList;
import kotetsu.auth.application.domain.repository.IFetchScopeInformationListPort;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.ScopeDescription;
import kotetsu.auth.application.domain.value.ScopeName;

@Component
public class ScopeInformationListRepository implements IFetchScopeInformationListPort{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScopeInformationListRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ScopeInformationList> fetch(RequestedScopeNameList nameList) {
        final List<String> scopeNameStrings = nameList.getValue()
            .stream()
            .map(scopeName -> scopeName.getValue())
            .collect(Collectors.toList());

        final String sql = """
            SELECT name, description
            FROM scopes
            WHERE name IN (:scope_names)
            ORDER BY created_at ASC;
        """;

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("scope_names", scopeNameStrings);
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        final List<ScopeInformation> scopeInformations = rows.stream()
            .map(row -> ScopeInformation.of(
                ScopeName.of(String.valueOf(row.get("name"))),
                ScopeDescription.of(String.valueOf(row.get("description")))
            ))
            .collect(Collectors.toList());

        return Optional.of(ScopeInformationList.of(scopeInformations));
    }
}
