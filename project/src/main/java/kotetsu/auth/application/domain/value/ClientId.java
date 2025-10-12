package kotetsu.auth.application.domain.value;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kotetsu.auth.application.domain.exception.ClientIdValidationRuntimeException;
import lombok.Getter;

public class ClientId {
    @Getter
    @NotNull
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}.kotetsu.com$")
    private final String value;

    private ClientId(final String value) {
        this.value = value;
    }

    public static ClientId of(final String value) {
        final ClientId clientId = new ClientId(value);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<ClientId>> violations = validator.validate(clientId);

        for (final ConstraintViolation<ClientId> violation : violations) {
            throw new ClientIdValidationRuntimeException(violation.getMessage());
        }
        
        return clientId;
    }
}
