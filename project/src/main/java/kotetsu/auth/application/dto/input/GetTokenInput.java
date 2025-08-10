package kotetsu.auth.application.dto.input;

import java.util.Optional;
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

    private final  String code;

    private final String codeVerifier;

    private final String refreshToken;

    public Optional<String> getCode() {
        return Optional.ofNullable(code);
    }

    public Optional<String> getCodeVerifier() {
        return Optional.ofNullable(codeVerifier);
    }

    public Optional<String> getRefreshToken() {
        return Optional.ofNullable(refreshToken);
    }

    private GetTokenInput(
        final String grantType,
        final String code,
        final String codeVerifier,
        final String refreshToken
    ) {
        this.grantType = grantType;
        this.code = code;
        this.codeVerifier = codeVerifier;
        this.refreshToken = refreshToken;
    }

    public static GetTokenInput of(
        final String grantType,
        final String code,
        final String codeVerifier,
        final String refreshToken
    ) {
        final GetTokenInput input = new GetTokenInput(
            grantType,
            code,
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