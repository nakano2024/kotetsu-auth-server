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
    private final AccessTokenBody accessTokenBody;

    @Getter
    @NotNull
    private final IdTokenBody idTokenBody;

    private RefreshTokenBody(
        final Id id,
        final AccessTokenBody accessTokenBody,
        final IdTokenBody idTokenBody
    ) {
        this.id = id;
        this.accessTokenBody= accessTokenBody;
        this.idTokenBody = idTokenBody;
    }

    public static RefreshTokenBody of(
        final Id id,
        final AccessTokenBody accessTokenBody,
        final IdTokenBody idTokenBody
    ) {
        RefreshTokenBody refreshTokenBody = new RefreshTokenBody(
            id,
            accessTokenBody,
            idTokenBody
        );

        return refreshTokenBody;
    }
}
