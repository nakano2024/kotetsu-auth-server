package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.Subject;
import lombok.Getter;

public class PendingInternalAuthToken {
    public static int EXPIRES_DAYS = 7;

    @Getter
    @NotNull
    private final MeProfile profile;

    @Getter
    @NotNull
    private final Duration duration;

    @Getter
    @NotNull
    private final Subject subject;

    private PendingInternalAuthToken(final Subject subject, final MeProfile profile, final Duration duration) {
        this.subject = subject;
        this.profile = profile;
        this.duration = duration;
    }

    public static PendingInternalAuthToken of(final Subject subject, final MeProfile profile, final Duration duration) {
        final PendingInternalAuthToken pendingInternalAuthToken = new PendingInternalAuthToken(
            subject,
            profile,
            duration
        );

        return pendingInternalAuthToken;
    }
}
