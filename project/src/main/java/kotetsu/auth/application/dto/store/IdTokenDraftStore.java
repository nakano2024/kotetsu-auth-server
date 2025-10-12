package kotetsu.auth.application.dto.store;

import java.util.UUID;

import lombok.Getter;

public class IdTokenDraftStore {
    @Getter
    private final UUID subject;

    @Getter
    private final String issuer;

    @Getter
    private final UUID audience;

    @Getter
    private final String nonce;

    private IdTokenDraftStore(final UUID subject, final String issuer, final UUID audience, final String nonce) {
        this.subject = subject;
        this.issuer = issuer;
        this.audience = audience;
        this.nonce = nonce;
    }

    public static IdTokenDraftStore of(final UUID subject, final String issuer, final UUID audience, final String nonce) {
        return new IdTokenDraftStore(subject, issuer, audience, nonce);
    }
}
