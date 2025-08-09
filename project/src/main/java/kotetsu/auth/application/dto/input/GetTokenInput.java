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

public class GetTokenInput {
    @Getter
    @Pattern(regexp = "[a-z_]+")
    @NotBlank
    private final String grantType;

    @Getter
    @Pattern(regexp = "[a-zA-Z0-9.]+")
    @NotBlank
    private final String clientId;

    @Getter
    private final String code;

    @Getter
    private final String codeVerifier;

    @Getter
    private final String refreshToken;

    private GetTokenInput(
        final String grantType,
        final String clientId,
        final String code,
        final String codeVerifier,
        final String refreshToken
    ) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.code = code;
        this.codeVerifier = codeVerifier;
        this.refreshToken = refreshToken;
    }

    public static GetTokenInput of(
        final String grantType,
        final String clientId,
        final String code,
        final String codeVerifier,
        final String refreshToken
    ) {
        final GetTokenInput input = new GetTokenInput(
            code,
            grantType,
            clientId,
            codeVerifier,
            refreshToken
        );

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<GetTokenInput>> violations = validator.validate(input);

        for (final ConstraintViolation<GetTokenInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}