package kotetsu.auth.application.domain.entity;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.PermittedScopeListValidationException;

public class PermittedScopeList {
    @NotNull
    private final Set<Scope> permittedScopes;

    private PermittedScopeList(final Set<Scope> scopes) {
        this.permittedScopes = scopes;
    }

    public static PermittedScopeList of(final Set<Scope> scopes) {
        final PermittedScopeList permittedScopeList = new PermittedScopeList(scopes);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<PermittedScopeList>> violations = validator.validate(permittedScopeList);
        for (final ConstraintViolation<PermittedScopeList> violation : violations) {
            throw new PermittedScopeListValidationException(violation.getMessage());
        }

        return permittedScopeList;
    }

    public boolean containsAll(final Set<Scope> requestedScopes) {
        if (requestedScopes == null) {
            return false;
        }

        if (requestedScopes.isEmpty()) {
            return false;
        }

        return permittedScopes.containsAll(requestedScopes);
    }
}
