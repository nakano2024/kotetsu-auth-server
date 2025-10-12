package kotetsu.auth.application.domain.entity;

import jakarta.validation.constraints.NotNull;
import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.GrantType;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.LinkedAccessTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedIdTokenCoreKey;
import kotetsu.auth.application.domain.value.LinkedRefreshTokenCoreKey;
import lombok.Getter;

public class ExistingAuthorization {
    @Getter
    @NotNull
    private final Key key;

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
    private final GrantType grantType;

    private ExistingAuthorization(
        final Key key,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final LinkedIdTokenCoreKey linkedIdTokenCoreKey,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey,
        final GrantType grantType
    ) {
        this.key = key;
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
        this.linkedAccessTokenCoreKey = linkedAccessTokenCoreKey;
        this.linkedIdTokenCoreKey = linkedIdTokenCoreKey;
        this.linkedRefreshTokenCoreKey = linkedRefreshTokenCoreKey;
        this.grantType = grantType;
    }

    public static ExistingAuthorization of(
        final Key key,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final LinkedAccessTokenCoreKey linkedAccessTokenCoreKey,
        final LinkedIdTokenCoreKey linkedIdTokenCoreKey,
        final LinkedRefreshTokenCoreKey linkedRefreshTokenCoreKey,
        final GrantType grantType
    ) {
        ExistingAuthorization authorization = new ExistingAuthorization(
            key,
            authorizationCode,
            accessType,
            linkedAccessTokenCoreKey,
            linkedIdTokenCoreKey,
            linkedRefreshTokenCoreKey,
            grantType
        );

        return authorization;
    }
}
