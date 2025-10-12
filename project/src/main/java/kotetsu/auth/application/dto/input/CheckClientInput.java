package kotetsu.auth.application.dto.input;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import kotetsu.auth.application.exception.InputException;
import lombok.Getter;

public class CheckClientInput {
    @Getter
    @NotBlank
    private final String clientId;

    @Getter
    @NotBlank
    private final String redirectUri;

    private CheckClientInput(final String clientId, final String redirectUri) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public static CheckClientInput of(final String clientId, final String redirectUri) {
        final CheckClientInput checkClientInput = new CheckClientInput(clientId, redirectUri);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CheckClientInput>> violations = validator.validate(checkClientInput);
        
        for (final ConstraintViolation<CheckClientInput> validation : violations) {
            throw new InputException(validation.getMessage());
        }
        
        return checkClientInput;
    }
}
