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
    private final Id accessTokenBodyId;

    @Getter
    @NotNull
    private final Id idTokenBodyId;

    @Getter
    @NotNull
    private final Id refreshTokenBodyId;

    private Authorization(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final Id accessTokenBodyId,
        final Id idTokenBodyId,
        final Id refreshTokenBodyId
    ) {
        this.id = id;
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
        this.accessTokenBodyId = accessTokenBodyId;
        this.idTokenBodyId = idTokenBodyId;
        this.refreshTokenBodyId = refreshTokenBodyId;
    }

    public static Authorization of(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType,
        final Id accessTokenBodyId,
        final Id idTokenBodyId,
        final Id refreshTokenBodyId
    ) {
        Authorization authorization = new Authorization(
            id,
            authorizationCode,
            accessType,
            accessTokenBodyId,
            idTokenBodyId,
            refreshTokenBodyId
        );

        return authorization;
    }
}
