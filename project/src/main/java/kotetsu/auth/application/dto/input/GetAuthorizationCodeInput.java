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

public class GetAuthorizationCodeInput {
    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String resourceOwnerCode;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String clientId;

    @Getter
    @NotBlank
    @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*\\??[^\\s]*")
    private final String redirectUri;

    @Getter
    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)( ([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*))*$")
    private final String pendingScopeString;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String codeChallenge;

    @Getter
    private final String nonce;

    private GetAuthorizationCodeInput(
        final String resourceOwnerCode,
        final String clientId,
        final String redirectUri,
        final String pendingScopeString,
        final String codeChallenge,
        final String nonce
    ) {
        this.resourceOwnerCode = resourceOwnerCode;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.pendingScopeString = pendingScopeString;
        this.codeChallenge = codeChallenge;
        this.nonce = nonce;
    }

    public static  GetAuthorizationCodeInput of(
        final String resourceOwnerCode,
        final String clientId,
        final String redirectUri,
        final String pendingScopeString,
        final String codeChallenge,
        final String nonce
    ) {
        final GetAuthorizationCodeInput input = new GetAuthorizationCodeInput(
            resourceOwnerCode,
            clientId,
            redirectUri,
            pendingScopeString,
            codeChallenge,
            nonce
        );

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetAuthorizationCodeInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetAuthorizationCodeInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}
