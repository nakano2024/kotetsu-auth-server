package kotetsu.auth.application.dto.input;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import kotetsu.auth.application.exception.InputException;
import lombok.Getter;

public class GetScopeDescriptionsInput {
    @Getter
    @NotBlank
    private final String scopeListToken;

    private GetScopeDescriptionsInput(final String scopeListToken) {
        this.scopeListToken = scopeListToken;
    }

    public static  GetScopeDescriptionsInput of(final String scopeListToken) {
        final GetScopeDescriptionsInput input = new GetScopeDescriptionsInput(scopeListToken);

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetScopeDescriptionsInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetScopeDescriptionsInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }  
}
