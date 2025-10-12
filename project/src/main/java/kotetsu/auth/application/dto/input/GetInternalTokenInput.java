package kotetsu.auth.application.dto.input;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.exception.InputException;
import lombok.Getter;

public class GetInternalTokenInput {
    @Getter
    @NotNull
    private final String userKey;

    private GetInternalTokenInput(final String userKey) {
        this.userKey = userKey;
    }

    public static GetInternalTokenInput of(final String userKey) {
        final GetInternalTokenInput input = new GetInternalTokenInput(userKey);

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetInternalTokenInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetInternalTokenInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}
