package kotetsu.auth.application.domain.entity;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.RequestedScopeAudienceWrapperValidationException;
import lombok.Getter;

public class RequestedScopeAudienceWrapper {
    @Getter
    @NotNull
    private final RequestedScopeList requestedScopeList;

    @Getter
    @NotNull
    private final RequestedScopeRelatedAudienceList requestedScopeRelatedAudienceList;

    private RequestedScopeAudienceWrapper(final RequestedScopeList requestedScopeList, final RequestedScopeRelatedAudienceList requestedScopeRelatedAudienceList) {
        this.requestedScopeList = requestedScopeList;
        this.requestedScopeRelatedAudienceList = requestedScopeRelatedAudienceList;
    }

    public static RequestedScopeAudienceWrapper of(final RequestedScopeList requestedScopeList, final RequestedScopeRelatedAudienceList requestedScopeRelatedAudienceList) {
        RequestedScopeAudienceWrapper requestedScopeAudienceWrapper = new RequestedScopeAudienceWrapper(
            requestedScopeList,
            requestedScopeRelatedAudienceList
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestedScopeAudienceWrapper>> violations = validator.validate(requestedScopeAudienceWrapper);

        for (final ConstraintViolation<RequestedScopeAudienceWrapper> violation : violations) {
            throw new RequestedScopeAudienceWrapperValidationException(violation.getMessage());
        }

        return requestedScopeAudienceWrapper;
    }
}
