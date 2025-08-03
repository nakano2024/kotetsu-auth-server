package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.Id;
import lombok.Getter;

public class Authorization {
    @Getter
    @NotNull
    private final Id id;

    @Getter
    @NotNull
    private final AuthorizationCode authorizationCode;

    @Getter
    @NotNull
    private final AccessType accessType;

    @Getter
    @NotNull
    private final AccessTokenBody accessTokenBody;

    @Getter
    @NotNull
    private final IdTokenBody idTokenBody;

    @Getter
    @NotNull
    private final RefreshTokenBody refreshTokenBody;

    private Authorization(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final AccessTokenBody accessTokenBody,
        final IdTokenBody idTokenBody,
        final RefreshTokenBody refreshTokenBody
    ) {
        this.id = id;
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
        this.accessTokenBody = accessTokenBody;
        this.idTokenBody = idTokenBody;
        this.refreshTokenBody = refreshTokenBody;
    }

    public static Authorization of(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final AccessTokenBody accessTokenBody,
        final IdTokenBody idTokenBody,
        final RefreshTokenBody refreshTokenBody
    ) {
        Authorization authorization = new Authorization(
            id,
            authorizationCode,
            accessType,
            accessTokenBody,
            idTokenBody,
            refreshTokenBody
        );

        return authorization;
    }
}
