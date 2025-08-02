package kotetsu.auth.application.domain.value;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.constant.ScopeNameConstant;
import kotetsu.auth.application.domain.exception.EnableOfflineAccessValidationException;
import lombok.Getter;

public class EnableOfflineAccess {
    @Getter
    @NotNull
    private final boolean value;

    private EnableOfflineAccess(final boolean value) {
        this.value = value;
    }

    public static EnableOfflineAccess of(RequestedScopeNameListToken requestedScopeNameListToken) {
        if (requestedScopeNameListToken == null) {
            throw new EnableOfflineAccessValidationException("requestedScopeNameListTokenは、nullが許容されていません。");
        }
        final List<ScopeName> requestedScopeNames = requestedScopeNameListToken.toScopeNameList();

        if (requestedScopeNames == null) {
            throw new EnableOfflineAccessValidationException("requestedScopeNamesは、nullが許容されていません。");
        }

        final EnableOfflineAccess enableOfflineAccess = new EnableOfflineAccess(
            requestedScopeNames.contains(ScopeName.of(ScopeNameConstant.OFFLINE_ACCESS))
        );

        return enableOfflineAccess;
    }
}
