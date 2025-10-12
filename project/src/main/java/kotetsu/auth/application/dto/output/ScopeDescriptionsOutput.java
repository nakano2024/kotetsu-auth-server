package kotetsu.auth.application.dto.output;

import java.util.List;

import lombok.Getter;

public class ScopeDescriptionsOutput {
    @Getter
    private final List<String> scopeDescriptions;

    private ScopeDescriptionsOutput(final List<String> scopeDescriptions) {
        this.scopeDescriptions = scopeDescriptions;
    }

    public static ScopeDescriptionsOutput of(final List<String> scopeDescriptions) {
        final ScopeDescriptionsOutput scopeDescriptionsOutput = new ScopeDescriptionsOutput(scopeDescriptions);
        return scopeDescriptionsOutput;
    }
}
