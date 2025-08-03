package kotetsu.auth.application.domain.value;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.constant.ScopeConstant;
import kotetsu.auth.application.domain.exception.EnableOfflineAccessValidationException;

public class EnableOfflineAccess {
    @NotNull
    private final boolean value;

    private EnableOfflineAccess(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public static EnableOfflineAccess of(RequestedScopeNameList requestedScopeNameList) {
        if (requestedScopeNameList == null) {
            throw new EnableOfflineAccessValidationException("requestedScopeNameListTokenは、nullが許容されていません。");
        }
        final Set<ScopeName> requestedScopeNames = requestedScopeNameList.getValue();

        if (requestedScopeNames == null) {
            throw new EnableOfflineAccessValidationException("requestedScopeNamesは、nullが許容されていません。");
        }

        final EnableOfflineAccess enableOfflineAccess = new EnableOfflineAccess(
            requestedScopeNames.contains(ScopeName.of(ScopeConstant.OFFLINE_ACCESS))
        );

        return enableOfflineAccess;
    }
}
