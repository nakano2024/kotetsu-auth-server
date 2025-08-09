package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import lombok.Getter;

public class PendingInternalAuthToken {
    public static int EXPIRES_DAYS = 7;

    @Getter
    @NotNull
    private final MeProfile profile;

    @Getter
    @NotNull
    private final Duration duration;

    private PendingInternalAuthToken(final MeProfile profile, final Duration duration) {
        this.profile = profile;
        this.duration = duration;
    }

    public static PendingInternalAuthToken of(final MeProfile profile, final Duration duration) {
        final PendingInternalAuthToken pendingInternalAuthToken = new PendingInternalAuthToken(profile, duration);

        return pendingInternalAuthToken;
    }
}
