package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import lombok.Getter;

public class ExistingIdTokenMeta {
    public static final int EXPIRES_HOURS = 1;

    @Getter
    @NotNull
    private final LinkedIdTokenCoreKey linkedIdTokenCoreKey;

    @Getter
    @NotNull
    private final Duration duration;

    @Getter
    @NotNull
    private final IdTokenUniqueId uniqueId;

    private ExistingIdTokenMeta(final LinkedIdTokenCoreKey linkedIdTokenCoreKey, final Duration duration, final IdTokenUniqueId uniqueId) {
        this.linkedIdTokenCoreKey = linkedIdTokenCoreKey;
        this.duration = duration;
        this.uniqueId = uniqueId;
    }

    public static ExistingIdTokenMeta of(final LinkedIdTokenCoreKey linkedIdTokenCoreKey, final Duration duration, final IdTokenUniqueId uniqueId) {
        final ExistingIdTokenMeta idTokenMeta = new ExistingIdTokenMeta(linkedIdTokenCoreKey, duration, uniqueId);

        return idTokenMeta;
    }
}
