package kotetsu.auth.application.domain.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.ScopeAudienceListValidationException;
import kotetsu.auth.application.domain.value.AccessTokenAudience;
import lombok.Getter;

public class ScopeAudienceList {
    @Getter
    @NotNull
    private final List<Scope> scopes;

    @Getter
    @NotNull
    private final Set<AccessTokenAudience> audiences;

    private ScopeAudienceList(final List<Scope> scopes, final Set<AccessTokenAudience> audiences) {
        this.scopes = scopes;
        this.audiences = audiences;
    }

    public static ScopeAudienceList of(final List<Scope> scopes, final List<AccessTokenAudience> audiences) {
        ScopeAudienceList scopeAudienceList = new ScopeAudienceList(
            scopes,
            new LinkedHashSet<>(audiences)
        );


        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<ScopeAudienceList>> violations = validator.validate(scopeAudienceList);

        for (final ConstraintViolation<ScopeAudienceList> violation : violations) {
            throw new ScopeAudienceListValidationException(violation.getMessage());
        }

        return scopeAudienceList;
    }
}
