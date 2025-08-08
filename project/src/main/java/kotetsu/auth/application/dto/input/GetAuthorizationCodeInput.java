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
    @Pattern(regexp = "^([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)( ([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*))*$")
    private final String clientKey;

    @Getter
    @NotBlank
    @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*\\??[^\\s]*")
    private final String redirectUri;

    @Getter
    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)( ([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*))*$")
    private final String scopeListToken;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String codeChallenge;

    @Getter
    private final String nonce;

    @Getter
    private final String accessType;

    private GetAuthorizationCodeInput(
        final String resourceOwnerCode,
        final String clientKey,
        final String redirectUri,
        final String scopeListToken,
        final String codeChallenge,
        final String nonce,
        final String accessType
    ) {
        this.resourceOwnerCode = resourceOwnerCode;
        this.clientKey = clientKey;
        this.redirectUri = redirectUri;
        this.scopeListToken = scopeListToken;
        this.codeChallenge = codeChallenge;
        this.nonce = nonce;
        this.accessType = accessType;
    }

    public static  GetAuthorizationCodeInput of(
        final String resourceOwnerCode,
        final String clientKey,
        final String redirectUri,
        final String scopeListToken,
        final String codeChallenge,
        final String nonce,
        final String accessType
    ) {
        final GetAuthorizationCodeInput input = new GetAuthorizationCodeInput(
            resourceOwnerCode,
            clientKey,
            redirectUri,
            scopeListToken,
            codeChallenge,
            nonce,
            accessType
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
