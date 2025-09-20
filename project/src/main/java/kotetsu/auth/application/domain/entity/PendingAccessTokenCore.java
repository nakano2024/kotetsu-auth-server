package kotetsu.auth.application.domain.entity;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.exception.AccessTokenDraftValidationException;
import kotetsu.auth.application.domain.value.ClientId;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class PendingAccessTokenCore {
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
    private final RequestedScopeList requestedScopeList;

    @Getter
    @NotNull
    private final ClientId requesterClientId;

    private PendingAccessTokenCore(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final RequestedScopeList requestedScopeList,
        final ClientId requesterClientId
    ) {
        this.key = key;
        this.issuer = issuer;
        this.subject = subject;
        this.requestedScopeList = requestedScopeList;
        this.requesterClientId = requesterClientId;
    }

    public static PendingAccessTokenCore of(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final RequestedScopeList requestedScopeList,
        final ClientId requesterClientId
    ) {

        final PendingAccessTokenCore accessTokenCore = new PendingAccessTokenCore(
            key,
            issuer,
            subject,
            requestedScopeList,
            requesterClientId
        );

        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<PendingAccessTokenCore>> violations = validator.validate(accessTokenCore);

        for (final ConstraintViolation<PendingAccessTokenCore> violation : violations) {
            throw new AccessTokenDraftValidationException(violation.getMessage());
        }

        return accessTokenCore;
    }
}
