package kotetsu.auth.application.domain.value;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kotetsu.auth.application.domain.exception.LinkedAccessTokenCoreKeyValidationRuntimeException;
import lombok.Getter;

public class LinkedAccessTokenCoreKey {
    @NotNull(message = "値はnullにできません")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
    @Getter
    private final String value;

    private LinkedAccessTokenCoreKey(final String value) {
        this.value = value;
    }

    public static LinkedAccessTokenCoreKey of(final String value) {
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey = new LinkedAccessTokenCoreKey(value);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<LinkedAccessTokenCoreKey>> violations = validator.validate(linkedAccessTokenCoreKey);
        for (final ConstraintViolation<LinkedAccessTokenCoreKey> violation : violations) {
            throw new LinkedAccessTokenCoreKeyValidationRuntimeException(violation.getMessage());
        }

        return linkedAccessTokenCoreKey;
    }
}
