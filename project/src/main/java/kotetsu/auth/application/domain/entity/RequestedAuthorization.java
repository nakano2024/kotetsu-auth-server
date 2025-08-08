package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import lombok.Getter;

public class RequestedAuthorization {
    @Getter
    @NotNull
    private final AuthorizationCode authorizationCode;

    @Getter
    @NotNull
    private final AccessType accessType;

    @Getter
    @NotNull
    private final LinkedAccessTokenCoreKey linkedAccessTokenBodyId;

    @Getter
    @NotNull
    private final LinkedIdTokenCoreKey linkedIdTokenBodyId;

    @Getter
    @NotNull
    private final LinkedRefreshTokenCoreKey linkedRefreshTokenBodyId;

    private RequestedAuthorization(
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenBodyId,
        final LinkedIdTokenCoreKey linkedIdTokenBodyId,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenBodyId
    ) {
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
        this.linkedAccessTokenBodyId = linkedAccessTokenBodyId;
        this.linkedIdTokenBodyId = linkedIdTokenBodyId;
        this.linkedRefreshTokenBodyId = linkedRefreshTokenBodyId;
    }

    public static RequestedAuthorization of(
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenBodyId,
        final LinkedIdTokenCoreKey linkedIdTokenBodyId,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenBodyId
    ) {
        RequestedAuthorization authorization = new RequestedAuthorization(
            authorizationCode,
            accessType,
            linkedAccessTokenBodyId,
            linkedIdTokenBodyId,
            linkedRefreshTokenBodyId
        );

        return authorization;
    }
}
