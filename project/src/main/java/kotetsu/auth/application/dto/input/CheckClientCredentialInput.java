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

public class CheckClientCredentialInput {
    @Getter
    @NotBlank
    @Pattern(regexp = "/^[A-Za-z0-9\\-_.~%]*$/")
    final String credentialToken;

    @Getter
    @NotBlank
    @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*\\??[^\\s]*")    
   final String redirectUri;  

    private CheckClientCredentialInput(final String credentialToken, final String redirectUri) {
        this.credentialToken = credentialToken;
        this.redirectUri = redirectUri;
    }

    public static CheckClientCredentialInput of(final String credentialToken, final String redirectUri) {

        final CheckClientCredentialInput base64CheckClientInput = new CheckClientCredentialInput(credentialToken, redirectUri);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CheckClientCredentialInput>> violations = validator.validate(base64CheckClientInput);
        
        for (final ConstraintViolation<CheckClientCredentialInput> validation : violations) {
            throw new InputException(validation.getMessage());
        }
        
        return base64CheckClientInput;
    }
}
