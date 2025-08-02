package kotetsu.auth.application.domain.entity;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.constant.ScopeNameConstant;
import kotetsu.auth.application.domain.exception.ResourceScopeNameListValidationException;
import kotetsu.auth.application.domain.value.RequestedScopeNameListToken;
import kotetsu.auth.application.domain.value.ScopeName;
import lombok.Getter;

public class ResourceScopeNameList {
    @Getter
    @NotNull
    private final Set<ScopeName> scopeNames;

    private ResourceScopeNameList(final Set<ScopeName> scopeNames) {
        this.scopeNames = scopeNames;
    }

    public static ResourceScopeNameList of(final RequestedScopeNameListToken scopeNameListToken) {
        if (scopeNameListToken == null) {
            throw new ResourceScopeNameListValidationException("scopeNameListTokenは、nullが許容されてません。");
        }

        Set<ScopeName> requestedScopeNames = scopeNameListToken.toScopeNameList().stream()
            .filter(requestedScopeName -> {
                return (
                    !requestedScopeName.equals(ScopeName.of(ScopeNameConstant.OPENID)) ||
                    !requestedScopeName.equals(ScopeName.of(ScopeNameConstant.OFFLINE_ACCESS))
                );
            })
            .collect(Collectors.toSet());
        
        ResourceScopeNameList resourceScopeNameList = new ResourceScopeNameList(requestedScopeNames);

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<ResourceScopeNameList>> violations = validator.validate(resourceScopeNameList);
        for (final ConstraintViolation<ResourceScopeNameList> violation : violations) {
            throw new ResourceScopeNameListValidationException(violation.getMessage());
        }

        return resourceScopeNameList;
    }
}
