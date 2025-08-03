package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.RequestedScopeAudienceWrapper;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;

public interface  IFetchScopeAudienceWrapperPort {
    RequestedScopeAudienceWrapper fetch(RequestedScopeNameList scopeNameList);
}
