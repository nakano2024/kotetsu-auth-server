package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Duration;
import kotetsu.auth.application.domain.value.IdTokenUniqueId;
import lombok.Getter;

public class IdTokenMeta {
    public static final int EXPIRES_HOURS = 1;

    @Getter
    @NotNull
    private final Duration duration;

    @Getter
    @NotNull
    private final IdTokenUniqueId uniqueId;

    private IdTokenMeta(final Duration duration, final IdTokenUniqueId uniqueId) {
        this.duration = duration;
        this.uniqueId = uniqueId;
    }

    public static IdTokenMeta of(final Duration duration, final IdTokenUniqueId uniqueId) {
        final IdTokenMeta idTokenMeta = new IdTokenMeta(duration, uniqueId);

        return idTokenMeta;
    }
}
