package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import kotetsu.auth.application.domain.value.Key;
import lombok.Getter;

public class ExistingIdTokenMeta {
    public static final int EXPIRES_HOURS = 1;

    @Getter
    @NotNull
    private final Key key;

    @Getter
    @NotNull
    private final Duration duration;

    @Getter
    @NotNull
    private final IdTokenUniqueId uniqueId;

    private ExistingIdTokenMeta(final Key key, final Duration duration, final IdTokenUniqueId uniqueId) {
        this.key = key;
        this.duration = duration;
        this.uniqueId = uniqueId;
    }

    public static ExistingIdTokenMeta of(final Key key, final Duration duration, final IdTokenUniqueId uniqueId) {
        final ExistingIdTokenMeta idTokenMeta = new ExistingIdTokenMeta(key, duration, uniqueId);

        return idTokenMeta;
    }
}
