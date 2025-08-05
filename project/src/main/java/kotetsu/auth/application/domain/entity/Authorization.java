package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.Id;
import kotetsu.auth.application.domain.value.LinkedAccessTokenBodyId;
import kotetsu.auth.application.domain.value.LinkedIdTokenBodyId;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenBodyId;
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
    private final LinkedAccessTokenBodyId linkedAccessTokenBodyId;

    @Getter
    @NotNull
    private final LinkedIdTokenBodyId linkedIdTokenBodyId;

    @Getter
    @NotNull
    private final LinkedRefreshTokenBodyId linkedRefreshTokenBodyId;

    private Authorization(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenBodyId linkedAccessTokenBodyId,
        final LinkedIdTokenBodyId linkedIdTokenBodyId,
        final LinkedRefreshTokenBodyId linkedRefreshTokenBodyId
    ) {
        this.id = id;
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
        this.linkedAccessTokenBodyId = linkedAccessTokenBodyId;
        this.linkedIdTokenBodyId = linkedIdTokenBodyId;
        this.linkedRefreshTokenBodyId = linkedRefreshTokenBodyId;
    }

    public static Authorization of(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenBodyId linkedAccessTokenBodyId,
        final LinkedIdTokenBodyId linkedIdTokenBodyId,
        final LinkedRefreshTokenBodyId linkedRefreshTokenBodyId
    ) {
        Authorization authorization = new Authorization(
            id,
            authorizationCode,
            accessType,
            linkedAccessTokenBodyId,
            linkedIdTokenBodyId,
            linkedRefreshTokenBodyId
        );

        return authorization;
    }
}
