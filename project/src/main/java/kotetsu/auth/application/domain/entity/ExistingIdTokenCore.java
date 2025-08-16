package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.IdTokenProfile;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class ExistingIdTokenCore {
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
    private final Nonce nonce;

    @Getter
    @NotNull
    private final IdTokenProfile profile;

    private ExistingIdTokenCore(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenProfile profile
    ) {
        this.key = key;
        this.issuer = issuer;
        this.subject = subject;
        this.nonce = nonce;
        this.profile = profile;
    }

    public static ExistingIdTokenCore of(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenProfile profile
    ) {
        ExistingIdTokenCore idTokenCore = new ExistingIdTokenCore(
            key,
            issuer,
            subject,
            nonce,
            profile
        );
        return idTokenCore;
    }
}
