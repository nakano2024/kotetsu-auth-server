package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.LinkedAccessTokenBodyId;
import kotetsu.auth.application.domain.value.LinkedIdTokenBodyId;
import lombok.Getter;

public class RefreshTokenBody {
    @Getter
    @NotNull
    private final Id id;

    @Getter
    @NotNull
    private final LinkedAccessTokenBodyId linkedAccessTokenBodyId;

    @Getter
    @NotNull
    private final LinkedIdTokenBodyId linkedIdTokenBodyId;

    private RefreshTokenBody(
        final Id id,
        final LinkedAccessTokenBodyId linkedAccessTokenBodyId,
        final LinkedIdTokenBodyId linkedIdTokenBodyId
    ) {
        this.id = id;
        this.linkedAccessTokenBodyId = linkedAccessTokenBodyId;
        this.linkedIdTokenBodyId = linkedIdTokenBodyId;
    }

    public static RefreshTokenBody of(
        final Id id,
        final LinkedAccessTokenBodyId linkedAccessTokenBodyId,
        final LinkedIdTokenBodyId linkedIdTokenBodyId
    ) {
        RefreshTokenBody refreshTokenBody = new RefreshTokenBody(
            id,
            linkedAccessTokenBodyId,
            linkedIdTokenBodyId
        );

        return refreshTokenBody;
    }
}
