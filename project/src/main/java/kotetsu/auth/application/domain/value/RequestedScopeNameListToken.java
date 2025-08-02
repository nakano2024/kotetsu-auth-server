package kotetsu.auth.application.domain.value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class RequestedScopeNameListToken {
    @NotBlank
    @Getter
    private final String value;

    private RequestedScopeNameListToken(final String value) {
        this.value = value;
    }

    public RequestedScopeNameListToken of(final String value) {
        RequestedScopeNameListToken requestedScopeNameListToken = new RequestedScopeNameListToken(value);

        return requestedScopeNameListToken;
    }

    public List<ScopeName> toScopeNameList() {
        List<String> scopeNameStrings = Arrays.asList(value.split(" "));
        return scopeNameStrings.stream()
            .map(scopeNameString -> ScopeName.of(scopeNameString))
            .collect(Collectors.toList());
    }
}
