package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.GrantType;
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
    private final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey;

    @Getter
    @NotNull
    private final LinkedIdTokenCoreKey linkedIdTokenCoreKey;

    @Getter
    @NotNull
    private final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey;

    @Getter
    @NotNull
    private final GrantType grantType;

    private RequestedAuthorization(
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final LinkedIdTokenCoreKey linkedIdTokenCoreKey,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey,
        final GrantType grantType
    ) {
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.linkedIdTokenCoreKey = linkedIdTokenCoreKey;
        this.linkedRefreshTokenCoreKey = linkedRefreshTokenCoreKey;
        this.grantType = grantType;
    }

    public static RequestedAuthorization of(
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final LinkedIdTokenCoreKey linkedIdTokenCoreKey,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey
    ) {
        RequestedAuthorization authorization = new RequestedAuthorization(
            authorizationCode,
            accessType,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey,
            linkedRefreshTokenCoreKey,
            GrantType.of(GrantType.GRANT_TYPE_AUTORIZATION_CODE)
        );

        return authorization;
    }
}
