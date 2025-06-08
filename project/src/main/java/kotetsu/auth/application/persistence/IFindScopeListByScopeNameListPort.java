package kotetsu.auth.application.persistence;

import java.util.List;

import kotetsu.auth.application.dto.data.ScopeData;

public interface IFindScopeListByScopeNameListPort {
    List<ScopeData> findByScopeNames(List<String> scopeNames);
}
