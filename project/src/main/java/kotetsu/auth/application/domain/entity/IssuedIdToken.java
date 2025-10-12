package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.IdTokenValue;
import lombok.Getter;

public class IssuedIdToken {
    @Getter
    @NotNull
    private final IdTokenValue value;

    private IssuedIdToken(final IdTokenValue value) {
        this.value = value;
    }

    public static IssuedIdToken of(final IdTokenValue value) {
        final IssuedIdToken idToken = new IssuedIdToken(value);

        return idToken;
    }
}
