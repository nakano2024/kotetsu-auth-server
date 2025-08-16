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
import kotetsu.auth.application.domain.exception.RequestedScopeRelatedAudienceListValidationException;
import kotetsu.auth.application.domain.value.AccessTokenAudience;

public class RequestedScopeRelatedAudienceList {
    @NotNull
    private final Set<AccessTokenAudience> audiences;

    private RequestedScopeRelatedAudienceList(Set<AccessTokenAudience> audiences) {
        this.audiences = audiences;
    }

    public static RequestedScopeRelatedAudienceList of(final List<String> audienceNameStrings) {
        // openidスコープなどaudienceに紐づかないスコープが含まれている場合nullとなるため、フィルタリング
        final List<AccessTokenAudience> audiences = audienceNameStrings.stream()
            .filter(audienceNameString -> audienceNameString != null)
            .map(audienceNameString -> AccessTokenAudience.of(audienceNameString))
            .collect(Collectors.toList());

        final RequestedScopeRelatedAudienceList requestedScopeRelatedAudienceList = new RequestedScopeRelatedAudienceList(
            new LinkedHashSet<>(audiences)
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestedScopeRelatedAudienceList>> violations = validator.validate(requestedScopeRelatedAudienceList);
        for (final ConstraintViolation<RequestedScopeRelatedAudienceList> violation : violations) {
            throw new RequestedScopeRelatedAudienceListValidationException(violation.getMessage());
        }

        return requestedScopeRelatedAudienceList;
    }

    public List<String> toStringList() {
        return audiences.stream()
            .map(AccessTokenAudience::getValue)
            .collect(Collectors.toList());
    }
}
