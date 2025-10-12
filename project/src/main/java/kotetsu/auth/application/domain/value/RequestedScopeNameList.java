package kotetsu.auth.application.domain.value;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.RequestedScopeNameListValidationException;
import lombok.Getter;

public class RequestedScopeNameList {
    @NotNull
    @Getter
    private final Set<ScopeName> value;

    private RequestedScopeNameList(Set<ScopeName> value) {
        this.value = value;
    }

    public static RequestedScopeNameList of(final RequestedScopeNameListToken requestedScopeNameListToken) {
        final List<String> scopeNameStringList = Arrays.asList(requestedScopeNameListToken.getValue().split(" "));

        final Set<ScopeName> value = scopeNameStringList.stream()
            .map(scopeNameString -> ScopeName.of(scopeNameString))
            .collect(Collectors.toSet());

        final RequestedScopeNameList requestedScopeNameList = new RequestedScopeNameList(value);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestedScopeNameList>> violations = validator.validate(requestedScopeNameList);
        for (final ConstraintViolation<RequestedScopeNameList> violation : violations) {
            throw new RequestedScopeNameListValidationException(violation.getMessage());
        }

        return requestedScopeNameList;
    }
}
