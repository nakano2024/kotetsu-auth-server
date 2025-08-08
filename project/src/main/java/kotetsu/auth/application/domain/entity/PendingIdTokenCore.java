package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.IdTokenAudience;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class PendingIdTokenCore {
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
    private final IdTokenAudience audience;

    private PendingIdTokenCore(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenAudience audience
    ) {
        this.key = key;
        this.issuer = issuer;
        this.subject = subject;
        this.nonce = nonce;
        this.audience = audience;
    }

    public static PendingIdTokenCore of(
        final Key key,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenAudience audience
    ) {
        final PendingIdTokenCore idTokenCore = new PendingIdTokenCore(
            key,
            issuer,
            subject,
            nonce,
            audience
        );
        return idTokenCore;
    }
}
