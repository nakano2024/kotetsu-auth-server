package kotetsu.auth.application.domain.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kotetsu.auth.application.domain.value.RequestedScopeNameList;
import kotetsu.auth.application.domain.value.ScopeName;

public class ScopeInformationList {
    private final Set<ScopeInformation> scopeInformations;

    private ScopeInformationList(final Set<ScopeInformation> scopeInformations) {
        this.scopeInformations = scopeInformations;
    }

    public static ScopeInformationList of(final List<ScopeInformation> scopeInformations) {
        final ScopeInformationList scopeInformationList = new ScopeInformationList(
            new LinkedHashSet<>(scopeInformations)
        );

        return scopeInformationList;
    }

    public boolean matchesRequestedScopeNameList(final RequestedScopeNameList requestedScopeNameList) {
        final Set<ScopeName> scopeNames = scopeInformations.stream()
            .map(scope -> scope.getName())
            .collect(Collectors.toSet());
        return scopeNames.containsAll(requestedScopeNameList.getValue());
    }

    public List<String> toStringDescriptionList() {
        return scopeInformations.stream()
            .map(scopeInformation -> scopeInformation.getDescription().getValue())
            .collect(Collectors.toList());
    }
}
