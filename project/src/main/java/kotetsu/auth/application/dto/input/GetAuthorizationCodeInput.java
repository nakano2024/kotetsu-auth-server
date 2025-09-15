package kotetsu.auth.application.dto.input;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import kotetsu.auth.application.exception.InputException;
import lombok.Getter;

public class GetAuthorizationCodeInput {
    @Getter
    @NotBlank
    private final String resourceOwnerKey;

    @Getter
    @NotBlank
    private final String clientKey;

    @Getter
    @NotBlank
    private final String redirectUri;

    @Getter
    @NotBlank
    private final String scopeListToken;

    @Getter
    @NotBlank
    private final String codeChallenge;

    @Getter
    private final String nonce;

    @Getter
    private final String accessType;

    private GetAuthorizationCodeInput(
        final String resourceOwnerKey,
        final String clientKey,
        final String redirectUri,
        final String scopeListToken,
        final String codeChallenge,
        final String nonce,
        final String accessType
    ) {
        this.resourceOwnerKey = resourceOwnerKey;
        this.clientKey = clientKey;
        this.redirectUri = redirectUri;
        this.scopeListToken = scopeListToken;
        this.codeChallenge = codeChallenge;
        this.nonce = nonce;
        this.accessType = accessType;
    }

    public static  GetAuthorizationCodeInput of(
        final String resourceOwnerKey,
        final String clientKey,
        final String redirectUri,
        final String scopeListToken,
        final String codeChallenge,
        final String nonce,
        final String accessType
    ) {
        final GetAuthorizationCodeInput input = new GetAuthorizationCodeInput(
            resourceOwnerKey,
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
