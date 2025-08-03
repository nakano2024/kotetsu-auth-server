package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.IdTokenAudience;
import kotetsu.auth.application.domain.value.Issuer;
import kotetsu.auth.application.domain.value.Nonce;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class IdTokenBody {
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
    private final IdTokenAudience audience;

    private IdTokenBody(
        final Id id,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenAudience audience
    ) {
        this.id = id;
        this.issuer = issuer;
        this.subject = subject;
        this.nonce = nonce;
        this.audience = audience;
    }

    public static IdTokenBody of(
        final Id id,
        final Issuer issuer,
        final Subject subject,
        final Nonce nonce,
        final IdTokenAudience audience
    ) {
        final IdTokenBody idTokenBody = new IdTokenBody(
            id,
            issuer,
            subject,
            nonce,
            audience
        );
        return idTokenBody;
    }
}
