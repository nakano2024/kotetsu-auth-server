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

public class GetUserCredentialEmailInput {
    @Getter
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private final String email;

    private GetUserCredentialEmailInput(String email) {
        this.email = email;
    }

    public static GetUserCredentialEmailInput of(String email) {
        final GetUserCredentialEmailInput input = new GetUserCredentialEmailInput(email);

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetUserCredentialEmailInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetUserCredentialEmailInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}
