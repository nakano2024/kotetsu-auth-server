package kotetsu.auth.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequestedScopeAudienceWrapper;
import kotetsu.auth.application.domain.repository.IFetchScopeAudienceWrapperPort;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;

public class ScopeAudienceWrapperRepository implements IFetchScopeAudienceWrapperPort {
    
    @Override
    public Optional<RequestedScopeAudienceWrapper> fetch(RequestedScopeNameList scopeNameList) {
        return Optional.empty();
    }
}