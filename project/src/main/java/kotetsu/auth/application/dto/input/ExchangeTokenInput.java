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
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String code;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-z_]+")
    private final String grantType;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9.]+")
    private final String clientId;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private final String clientSecret;

    @Getter
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_-]+")
    private final String codeVerifier;

    @Getter
    @NotBlank
    @Pattern(regexp = "https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[/\\w\\.-]*\\??[^\\s]*")
    private final String redirectUri;

    private ExchangeTokenInput(
        final String code,
        final String grantType,
        final String clientId,
        final String clientSecret,
        final String codeVerifier,
        final String redirectUri
    ) {
        this.code = code;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.codeVerifier = codeVerifier;
        this.redirectUri = redirectUri;
    }

    public static ExchangeTokenInput of(
        final String code,
        final String grantType,
        final String clientId,
        final String clientSecret,
        final String codeVerifier,
        final String redirectUri
    ) {
        final ExchangeTokenInput input = new ExchangeTokenInput(
            code,
            grantType,
            clientId,
            clientSecret,
            codeVerifier,
            redirectUri
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