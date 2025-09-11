package kotetsu.auth.application.domain.value;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class AuthorizationCodeChallenge {
    @Getter
    @NotBlank
    private final String value;

    private AuthorizationCodeChallenge(final String value) {
        this.value = value;
    }

    public static AuthorizationCodeChallenge of(final String value) {
        final AuthorizationCodeChallenge authorizationCodeChallenge = new AuthorizationCodeChallenge(value);
        return authorizationCodeChallenge;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final AuthorizationCodeChallenge anotherAuthorizationCodeChallenge = (AuthorizationCodeChallenge) obj;

        return this.value.equals(anotherAuthorizationCodeChallenge.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
