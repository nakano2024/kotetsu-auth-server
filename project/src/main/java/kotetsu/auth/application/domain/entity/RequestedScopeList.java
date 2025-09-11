package kotetsu.auth.application.domain.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.RequestedScopeListValidationException;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.ScopeName;
import lombok.Getter;

public class RequestedScopeList {
    @Getter
    @NotNull
    private final Set<Scope> scopes;

    private RequestedScopeList(final Set<Scope> scopes) {
        this.scopes = scopes;
    }

    public static RequestedScopeList of(final List<Scope> scopes) { 
        final RequestedScopeList requestedScopeList = new RequestedScopeList(
            new LinkedHashSet<>(scopes)
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestedScopeList>> violations = validator.validate(requestedScopeList);
        for (final ConstraintViolation<RequestedScopeList> violation : violations) {
            throw new RequestedScopeListValidationException(violation.getMessage());
        }

        return requestedScopeList;
    }

    public boolean hasOpenid() {
        return scopes.contains(Scope.of(Key.of(Scope.KEY_OPENID), ScopeName.of(Scope.NAME_OPENID)));
    }

    public String toScopeListToken() {
        final List<String> scopeNameStrings = scopes.stream()
            .map(scope -> scope.getName().getValue())
            .collect(Collectors.toList());
        
        return String.join(" ", scopeNameStrings);
    }
}
