package kotetsu.auth.application.domain.entity;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.AccessTokenDraftValidationException;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class ExistingAccessTokenCore {
    @NotNull
    @Getter
    private final Key key;

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

    private ExistingAccessTokenCore(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final RequestedScopeList scopeList,
        final RequestedScopeRelatedAudienceList relatedAudienceList
    ) {
        this.key = key;
        this.issuer = issuer;
        this.subject = subject;
        this.scopeList = scopeList;
        this.relatedAudienceList = relatedAudienceList;
    }

    public static ExistingAccessTokenCore of(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final RequestedScopeList scopeList,
        final RequestedScopeRelatedAudienceList relatedAudienceList
    ) {

        final ExistingAccessTokenCore accessTokenCore = new ExistingAccessTokenCore(
            key,
            issuer,
            subject,
            scopeList,
            relatedAudienceList
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<ExistingAccessTokenCore>> violations = validator.validate(accessTokenCore);

        for (final ConstraintViolation<ExistingAccessTokenCore> violation : violations) {
            throw new AccessTokenDraftValidationException(violation.getMessage());
        }

        return accessTokenCore;
    }
}
