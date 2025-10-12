package kotetsu.auth.application.domain.repository;

import java.util.Optional;

import kotetsu.auth.application.domain.entity.ScopeInformationList;
import kotetsu.auth.application.domain.value.RequestedScopeNameList;

public interface  IFetchScopeInformationListPort {
    Optional<ScopeInformationList> fetch(RequestedScopeNameList nameList);
}
