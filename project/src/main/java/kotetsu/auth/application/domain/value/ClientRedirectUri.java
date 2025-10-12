package kotetsu.auth.application.domain.value;

import java.util.Objects;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import kotetsu.auth.application.domain.exception.RequestedScopeNameListTokenValidationException;
import lombok.Getter;

public class ClientRedirectUri {
    @Getter
    @NotBlank
    private final String value;

    private ClientRedirectUri(final String value) {
        this.value = value;
    }

    public static ClientRedirectUri of(final String value) {
        final ClientRedirectUri clientRedirectUri = new ClientRedirectUri(value);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<ClientRedirectUri>> violations = validator.validate(clientRedirectUri);

        for (final ConstraintViolation<ClientRedirectUri> violation : violations) {
            throw new RequestedScopeNameListTokenValidationException(violation.getMessage());
        }

        return clientRedirectUri;
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

        final ClientRedirectUri anotherClientRedirectUri = (ClientRedirectUri) obj;

        return this.getValue().equals(anotherClientRedirectUri.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
