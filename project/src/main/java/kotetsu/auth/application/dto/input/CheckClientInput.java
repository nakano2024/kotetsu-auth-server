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

public class CheckClientInput {
    @Getter
    @Pattern(regexp = "[a-zA-Z0-9.]+")
    @NotBlank
    private final String clientId;

    @Getter
    @Pattern(regexp = "[a-zA-Z0-9]+")
    @NotBlank
    private final String clientSecret;

    @Getter
    @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*\\??[^\\s]*")
    @NotBlank
    private final String redirectUri;

    private CheckClientInput(final String clientId, final String clientSecret, final String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public static CheckClientInput of(final String clientId, final String clientSecret, final String redirectUri) {
        final CheckClientInput checkClientInput = new CheckClientInput(clientId, clientSecret, redirectUri);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CheckClientInput>> violations = validator.validate(checkClientInput);
        
        for (final ConstraintViolation<CheckClientInput> validation : violations) {
            throw new InputException(validation.getMessage());
        }
        
        return checkClientInput;
    }
}
