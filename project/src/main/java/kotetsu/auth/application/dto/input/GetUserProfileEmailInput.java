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

public class GetUserProfileEmailInput {
    @Getter
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private final String email;

    private GetUserProfileEmailInput(String email) {
        this.email = email;
    }

    public static GetUserProfileEmailInput of(String email) {
        final GetUserProfileEmailInput input = new GetUserProfileEmailInput(email);

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetUserProfileEmailInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetUserProfileEmailInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}
