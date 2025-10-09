package kotetsu.auth.application.domain.value;

import java.util.Objects;

import lombok.Getter;

public class ScopeDescription {
    @Getter
    private final String value;

    private ScopeDescription(final String value) {
        this.value = value;
    }

    public static ScopeDescription of(final String value) {
        final ScopeDescription scopeDescription = new ScopeDescription(value);
        return scopeDescription;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }
        
        final ScopeDescription otherScopeDescription = (ScopeDescription) obj;

        return super.equals(this.getValue().equals(otherScopeDescription.getValue()));
    }
}
