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

public class AuthCodeExchangeInput {
    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String code;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_-]+")
    private final String codeVerifier;

    private AuthCodeExchangeInput(final String code, final String codeVerifier) {
        this.code = code;
        this.codeVerifier = codeVerifier;
    }

    public static AuthCodeExchangeInput of(final String code, final String codeVerifier) {

        final AuthCodeExchangeInput authCodeExchangeInput = new AuthCodeExchangeInput(code, codeVerifier);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AuthCodeExchangeInput>> violations = validator.validate(authCodeExchangeInput);
        
        for (final ConstraintViolation<AuthCodeExchangeInput> validation : violations) {
            throw new InputException(validation.getMessage());
        }
        
        return authCodeExchangeInput;
    }
}
