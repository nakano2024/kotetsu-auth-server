package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.ResourceScopeNameList;
import kotetsu.auth.application.domain.entity.ScopeAudienceList;

public interface  IFetchScopeAudienceListPort {
    ScopeAudienceList fetch(ResourceScopeNameList scopeNameList);
}
