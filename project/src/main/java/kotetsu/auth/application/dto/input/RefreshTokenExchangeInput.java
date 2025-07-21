package kotetsu.auth.application.dto.input;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kotetsu.auth.application.exception.InputException;
import lombok.Getter;

public class RefreshTokenExchangeInput {
    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String refreshToken;

    private RefreshTokenExchangeInput(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static RefreshTokenExchangeInput of(final String refreshToken) {

        RefreshTokenExchangeInput input = new RefreshTokenExchangeInput(refreshToken);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RefreshTokenExchangeInput>> violations = validator.validate(input);

        for (ConstraintViolation<RefreshTokenExchangeInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }
        
        return input;
    }
}
