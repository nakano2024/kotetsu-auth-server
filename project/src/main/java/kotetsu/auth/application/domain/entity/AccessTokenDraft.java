package kotetsu.auth.application.domain.entity;

import java.util.Set;

import javax.security.auth.Subject;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.AccessTokenDraftValidationException;
import kotetsu.auth.application.domain.value.Code;
import kotetsu.auth.application.domain.value.Issuer;
import lombok.Getter;

public class AccessTokenDraft {
    @Getter
    @NotNull
    private final Code code;

    @Getter
    @NotNull
    private final Issuer issuer;

    @Getter
    @NotNull
    private final Subject subject;

    @Getter
    @NotNull
    private final ScopeAudienceList scopesAudiencesSet;

    private AccessTokenDraft(
        final Code code,
        final Issuer issuer,
        final Subject subject,
        final ScopeAudienceList scopesAudiencesSet
    ) {
        this.code = code;
        this.issuer = issuer;
        this.subject = subject;
        this.scopesAudiencesSet = scopesAudiencesSet;
    }

    public static AccessTokenDraft of(
        final Code code,
        final Issuer issuer,
        final Subject subject,
        final ScopeAudienceList scopesAudiencesSet 
    ) {

        final AccessTokenDraft accessTokenDraft = new AccessTokenDraft(
            code,
            issuer,
            subject,
            scopesAudiencesSet
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<AccessTokenDraft>> violations = validator.validate(accessTokenDraft);

        for (final ConstraintViolation<AccessTokenDraft> violation : violations) {
            throw new AccessTokenDraftValidationException(violation.getMessage());
        }

        return accessTokenDraft;
    }
}
