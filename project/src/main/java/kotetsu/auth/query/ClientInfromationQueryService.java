package kotetsu.auth.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import kotetsu.auth.application.dto.data.ClientInformationData;
import kotetsu.auth.application.query.IFindClientInformationPort;

@Component
public class ClientInfromationQueryService implements IFindClientInformationPort{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClientInfromationQueryService(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ClientInformationData> findByClientId(String clientId) {
        final String sql = """
            SELECT client_id, name
            FROM clients
            WHERE client_id = :client_id;
        """;

        final Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        final Map<String, Object> row = rows.get(0);

        return Optional.of(ClientInformationData.of(
            String.valueOf(row.get("client_id")),
            String.valueOf(row.get("name"))
        ));
    }
}
