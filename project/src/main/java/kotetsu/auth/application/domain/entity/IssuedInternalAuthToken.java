package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.InternalAuthTokenValue;
import lombok.Getter;

public class IssuedInternalAuthToken {
    public static final String TOKEN_TYPE = "Bearer";

    @Getter
    @NotNull
    private final InternalAuthTokenValue value;

    private IssuedInternalAuthToken(final InternalAuthTokenValue value) {
        this.value = value;
    }

    public static IssuedInternalAuthToken of(final InternalAuthTokenValue value) {
        final IssuedInternalAuthToken internalAuthToken = new IssuedInternalAuthToken(value);

        return internalAuthToken;
    }
}
