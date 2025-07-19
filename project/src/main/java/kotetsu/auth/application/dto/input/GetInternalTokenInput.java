package kotetsu.auth.application.dto.input;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kotetsu.auth.application.exception.InputException;
import lombok.Getter;

public class GetInternalTokenInput {
    @Getter
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private final String email;

    private GetInternalTokenInput(String email) {
        this.email = email;
    }

    public static GetInternalTokenInput of(String email) {
        final GetInternalTokenInput input = new GetInternalTokenInput(email);

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetInternalTokenInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetInternalTokenInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}
