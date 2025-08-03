package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class IdTokenBodyView {
    @NotNull
    @Getter
    private final Id id;

    @Getter
    @NotNull
    private final Issuer issuer;

    @Getter
    @NotNull
    private final Subject subject;

    @Getter
    @NotNull
    private final Nonce nonce;

    @Getter
    @NotNull
    private final IdTokenProfile profile;

    private IdTokenBodyView(
        final Id id,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenProfile profile
    ) {
        this.id = id;
        this.issuer = issuer;
        this.subject = subject;
        this.nonce = nonce;
        this.profile = profile;
    }

    public static IdTokenBodyView of(
        final Id id,
        final Id authorizationInformationId,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenProfile profile
    ) {
        IdTokenBodyView idTokenBodyView = new IdTokenBodyView(
            id,
            issuer,
            subject,
            nonce,
            profile
        );
        return idTokenBodyView;
    }
}
