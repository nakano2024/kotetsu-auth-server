package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.AccessType;
import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.Id;
import lombok.Getter;

public class AuthorizationInformation {
    @Getter
    private final Id id;

    @Getter
    private final AuthorizationCode authorizationCode;

    @Getter
    private final AccessType accessType;

    private AuthorizationInformation(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType
    ) {
        this.id = id;
        this.authorizationCode = authorizationCode;
        this.accessType = accessType;
    }

    public static AuthorizationInformation of(
        final Id id,
        final AuthorizationCode authorizationCode,
        final AccessType accessType
    ) {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(
            id,
            authorizationCode,
            accessType
        );

        return authorizationInformation;
    }
}
