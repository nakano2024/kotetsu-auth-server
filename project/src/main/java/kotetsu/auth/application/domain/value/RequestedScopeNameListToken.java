package kotetsu.auth.application.domain.value;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kotetsu.auth.application.domain.exception.RequestedScopeNameListTokenValidationException;
import lombok.Getter;

public class RequestedScopeNameListToken {
    @Getter
    @NotNull
    @Pattern(regexp = "^(?:[A-Za-z0-9]+\\.[A-Za-z0-9]+(?: [A-Za-z0-9]+\\.[A-Za-z0-9]+)*|[A-Za-z0-9]+(?: [A-Za-z0-9]+)*)$")
    private final String value;

    private RequestedScopeNameListToken(final String value) {
        this.value = value;
    }

    public static RequestedScopeNameListToken of(final String value) {
        final RequestedScopeNameListToken requestedScopeNameListToken = new RequestedScopeNameListToken(value);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<RequestedScopeNameListToken>> violations = validator.validate(requestedScopeNameListToken);

        for (final ConstraintViolation<RequestedScopeNameListToken> violation : violations) {
            throw new RequestedScopeNameListTokenValidationException(violation.getMessage());
        }
        
        return requestedScopeNameListToken;
    }
}
