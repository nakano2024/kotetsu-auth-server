package kotetsu.auth.application.dto.data;

import java.util.UUID;

import lombok.Getter;

public class IdTokenDraftData {
    @Getter
    private final UUID code;

    @Getter
    private final String subject;

    @Getter
    private final String audience;

    @Getter
    private final IdTokenProfileData profile;

    private IdTokenDraftData(
        final UUID code,
        final String subject,
        final String audience,
        final IdTokenProfileData profile
    ) {
        this.code = code;
        this.subject = subject;
        this.audience = audience;
        this.profile = profile;
    }

    public static IdTokenDraftData of(
        final UUID code,
        final String subject,
        final String audience,
        final IdTokenProfileData profile
    ) {
        return new IdTokenDraftData(code, subject, audience, profile);
    }
}
