package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class IdTokenDraftData {
    @Getter
    private final UUID code;

    @Getter
    private final UUID subject;

    @Getter
    private final UUID audience;

    @Getter
    private final IdTokenProfileData profile;

    @Getter
    private final String nonce;

    private IdTokenDraftData(
        final UUID code,
        final UUID subject,
        final UUID audience,
        final IdTokenProfileData profile,
        final String nonce
    ) {
        this.code = code;
        this.subject = subject;
        this.audience = audience;
        this.profile = profile;
        this.nonce = nonce;
    }

    public static IdTokenDraftData of(
        final UUID code,
        final UUID subject,
        final UUID audience,
        final IdTokenProfileData profile,
        final String nonce
    ) {
        return new IdTokenDraftData(code, subject, audience, profile, nonce);
    }
}
