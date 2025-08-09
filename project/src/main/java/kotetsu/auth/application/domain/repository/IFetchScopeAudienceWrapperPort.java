package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequestedScopeAudienceWrapper;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;

public interface  IFetchScopeAudienceWrapperPort {
    Optional<RequestedScopeAudienceWrapper> fetch(RequestedScopeNameList scopeNameList);
}
