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

public class ExchangeTokenInput {
    @Getter
    @Pattern(regexp = "[a-z_]+")
    @NotBlank
    private final String grantType;

    @Getter
    @Pattern(regexp = "[a-zA-Z0-9.]+")
    @NotBlank
    private final String clientId;

    @Getter
    private final String clientSecret;

    @Getter
    private final String clientCredentialToken;

    @Getter
    private final String redirectUri;

    @Getter
    private final String code;

    @Getter
    private final String codeVerifier;

    @Getter
    private final String refreshToken;

    private ExchangeTokenInput(
        final String code,
        final String grantType,
        final String clientId,
        final String clientSecret,
        final String clientCredentialToken,
        final String codeVerifier,
        final String redirectUri,
        final String refreshToken
    ) {
        this.code = code;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientCredentialToken = clientCredentialToken;
        this.codeVerifier = codeVerifier;
        this.redirectUri = redirectUri;
        this.refreshToken = redirectUri;
    }

    public static ExchangeTokenInput of(
        final String code,
        final String grantType,
        final String clientId,
        final String clientSecret,
        final String clientCredentialToken,
        final String codeVerifier,
        final String redirectUri,
        final String refreshToken
    ) {
        final ExchangeTokenInput input = new ExchangeTokenInput(
            code,
            grantType,
            clientId,
            clientSecret,
            clientCredentialToken,
            codeVerifier,
            redirectUri,
            refreshToken
        );

        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<ExchangeTokenInput>> violations = validator.validate(input);

        for (final ConstraintViolation<ExchangeTokenInput> violation : violations) {
            throw new InputException(violation.getMessage());
        }

        return input;
    }
}