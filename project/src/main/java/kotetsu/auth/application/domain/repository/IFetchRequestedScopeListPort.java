package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.RequestedScopeList;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;

public interface  IFetchRequestedScopeListPort {
    Optional<RequestedScopeList> fetch(RequestedScopeNameList scopeNameList);
}
