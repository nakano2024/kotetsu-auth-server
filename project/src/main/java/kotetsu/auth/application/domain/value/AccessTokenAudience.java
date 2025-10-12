package kotetsu.auth.application.domain.value;

import java.util.Objects;

import lombok.Getter;

public class AccessTokenAudience {
    @Getter
    private final String value;

    private AccessTokenAudience(final String value) {
        this.value = value;
    }

    public static AccessTokenAudience of(final String value) {
        final AccessTokenAudience audience = new AccessTokenAudience(value);
        return audience;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (object.getClass() != this.getClass()) {
            return false;
        }

        AccessTokenAudience anotherAudience = (AccessTokenAudience) object;
        return this.value.equals(anotherAudience.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
