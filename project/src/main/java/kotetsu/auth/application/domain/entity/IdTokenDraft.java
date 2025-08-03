package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class IdTokenDraft {
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
    private final IdTokenProfile profile;

    @Getter
    @NotNull
    private final Nonce nonce;

    private IdTokenDraft(
        final Id authorizationInformationId,
        final Issuer issuer,
        final Subject subject,
        final IdTokenProfile profile,
        final Nonce nonce
    ) {
        this.authorizationInformationId = authorizationInformationId;
        this.issuer = issuer;
        this.subject = subject;
        this.profile = profile;
        this.nonce = nonce;
    }

    public static IdTokenDraft of(
        final Id authorizationInformationId,
        final Issuer issuer,
        final Subject subject,
        final IdTokenProfile profile,
        final Nonce nonce
    ) {
        IdTokenDraft idTokenDraft = new IdTokenDraft(
            authorizationInformationId,
            issuer,
            subject,
            profile,
            nonce
        );
        return idTokenDraft;
    }
}
