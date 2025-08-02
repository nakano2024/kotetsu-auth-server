package kotetsu.auth.application.domain.value;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.constant.ScopeNameConstant;
import kotetsu.auth.application.domain.exception.EnableOpenIdValidationException;
import lombok.Getter;

public class EnableOpenId {
    @Getter
    @NotNull
    private final boolean value;

    private EnableOpenId(final boolean value) {
        this.value = value;
    }

    public static EnableOpenId of(RequestedScopeNameListToken requestedScopeNameListToken) {
        if (requestedScopeNameListToken == null) {
            throw new EnableOpenIdValidationException("requestedScopeNameListTokenは、nullが許容されていません。");
        }
        final List<ScopeName> requestedScopeNames = requestedScopeNameListToken.toScopeNameList();

        if (requestedScopeNames == null) {
            throw new EnableOpenIdValidationException("requestedScopeNamesは、nullが許容されていません。");
        }

        final EnableOpenId enableOpenId = new EnableOpenId(
            requestedScopeNames.contains(ScopeName.of(ScopeNameConstant.OPENID))
        );

        return enableOpenId;
    }
}
