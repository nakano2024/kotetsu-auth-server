package kotetsu.auth.application.dto.store;

import java.util.UUID;

import lombok.Getter;

public class IdTokenDraftStore {
    @Getter
    private final UUID subject;

    @Getter
    private final String issuer;

    @Getter
    private final String audience;

    private IdTokenDraftStore(final UUID subject, final String issuer, final String audience) {
        this.subject = subject;
        this.issuer = issuer;
        this.audience = audience;
    }

    public static IdTokenDraftStore of(final UUID subject, final String issuer, final String audience) {
        return new IdTokenDraftStore(subject, issuer, audience);
    }
}
