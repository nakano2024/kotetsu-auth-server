package kotetsu.auth.application.domain.value;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.constant.ScopeConstant;
import kotetsu.auth.application.domain.exception.EnableOpenIdValidationException;

public class EnableOpenId {
    @NotNull
    private final boolean value;

    private EnableOpenId(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public static EnableOpenId of(RequestedScopeNameList requestedScopeNameList) {
        if (requestedScopeNameList == null) {
            throw new EnableOpenIdValidationException("requestedScopeNameListTokenは、nullが許容されていません。");
        }
        final Set<ScopeName> requestedScopeNames = requestedScopeNameList.getValue();

        if (requestedScopeNames == null) {
            throw new EnableOpenIdValidationException("requestedScopeNamesは、nullが許容されていません。");
        }

        final EnableOpenId enableOpenId = new EnableOpenId(
            requestedScopeNames.contains(ScopeName.of(ScopeConstant.OPENID))
        );

        return enableOpenId;
    }
}
