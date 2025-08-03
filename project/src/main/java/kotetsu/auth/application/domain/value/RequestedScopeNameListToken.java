package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class RequestedScopeNameListToken {
    @Getter
    private final String value;

    private RequestedScopeNameListToken(final String value) {
        this.value = value;
    }

    public static RequestedScopeNameListToken of(final String value) {
        RequestedScopeNameListToken requestedScopeNameListToken = new RequestedScopeNameListToken(value);
        return requestedScopeNameListToken;
    }
}
