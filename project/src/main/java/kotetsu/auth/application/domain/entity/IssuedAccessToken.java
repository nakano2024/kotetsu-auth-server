package kotetsu.auth.application.domain.entity;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.IssuedAccessTokenValidationRuntimeException;
import kotetsu.auth.application.domain.value.AccessTokenValue;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import lombok.Getter;

public class IssuedAccessToken {
    public static final String TOKEN_TYPE = "Bearer";
    public static final int EXPIRES_HOURS = 1;

    @Getter
    @NotNull
    final AccessTokenValue value;

    @Getter
    @NotNull
    final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey;

    @Getter
    @NotNull
    final Duration duration;

    private IssuedAccessToken(
        final AccessTokenValue value,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        this.value = value;
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.duration = duration;
    }

    public static IssuedAccessToken of(
        final AccessTokenValue value,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final Duration duration
    ) {
        final IssuedAccessToken pendingAccessToken = new IssuedAccessToken(value, linkedAccessTokenCoreKey, duration);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<IssuedAccessToken>> violations = validator.validate(pendingAccessToken);
        for (final ConstraintViolation<IssuedAccessToken> violation : violations) {
            throw new IssuedAccessTokenValidationRuntimeException(violation.getMessage());
        }
        
        return pendingAccessToken;
    }
}
