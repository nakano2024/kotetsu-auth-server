package kotetsu.auth.application.domain.entity;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.AccessTokenDraftValidationException;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class AccessTokenBody {
    @NotNull
    @Getter
    private final Id authorizationInformationId;

    @Getter
    @NotNull
    private final Issuer issuer;

    @Getter
    @NotNull
    private final Subject subject;

    @Getter
    @NotNull
    private final RequestedScopeList scopeList;

    @Getter
    @NotNull
    private final RequestedScopeRelatedAudienceList relatedAudienceList;

    private AccessTokenBody(
        final Id authorizationInformationId,
        final Issuer issuer,
        final Subject subject,
        final RequestedScopeList scopeList,
        final RequestedScopeRelatedAudienceList relatedAudienceList
    ) {
        this.authorizationInformationId = authorizationInformationId;
        this.issuer = issuer;
        this.subject = subject;
        this.scopeList = scopeList;
        this.relatedAudienceList = relatedAudienceList;
    }

    public static AccessTokenBody of(
        final Id authorizationInformationId,
        final Issuer issuer,
        final Subject subject,
        final RequestedScopeList scopeList,
        final RequestedScopeRelatedAudienceList relatedAudienceList
    ) {

        final AccessTokenBody accessTokenBody = new AccessTokenBody(
            authorizationInformationId,
            issuer,
            subject,
            scopeList,
            relatedAudienceList
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<AccessTokenBody>> violations = validator.validate(accessTokenBody);

        for (final ConstraintViolation<AccessTokenBody> violation : violations) {
            throw new AccessTokenDraftValidationException(violation.getMessage());
        }

        return accessTokenBody;
    }
}
