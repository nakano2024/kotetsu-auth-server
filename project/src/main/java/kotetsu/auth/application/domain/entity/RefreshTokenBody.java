package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.Id;
import lombok.Getter;

public class RefreshTokenBody {
    @Getter
    @NotNull
    private final Id id;

    @Getter
    @NotNull
    private final Id accessTokenBodyId;

    @Getter
    @NotNull
    private final Id idTokenBodyId;

    private RefreshTokenBody(
        final Id id,
        final Id accessTokenBodyId,
        final Id idTokenBodyId
    ) {
        this.id = id;
        this.accessTokenBodyId = accessTokenBodyId;
        this.idTokenBodyId = idTokenBodyId;
    }

    public static RefreshTokenBody of(
        final Id id,
        final Id accessTokenBodyId,
        final Id idTokenBodyId
    ) {
        RefreshTokenBody refreshTokenBody = new RefreshTokenBody(
            id,
            accessTokenBodyId,
            idTokenBodyId
        );

        return refreshTokenBody;
    }
}
