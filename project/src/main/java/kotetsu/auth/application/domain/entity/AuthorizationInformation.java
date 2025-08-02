package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.AuthorizationCode;
import kotetsu.auth.application.domain.value.EnableOfflineAccess;
import kotetsu.auth.application.domain.value.EnableOpenId;
import kotetsu.auth.application.domain.value.Id;
import lombok.Getter;

public class AuthorizationInformation {
    @Getter
    private final Id id;

    @Getter
    private final AuthorizationCode authorizationCode;

    @Getter
    private final EnableOpenId enableOpenId;

    @Getter
    private final EnableOfflineAccess enableOfflineAccess;

    private AuthorizationInformation(
        final Id id,
        final AuthorizationCode authorizationCode,
        final EnableOpenId enableOpenId,
        final EnableOfflineAccess enableOfflineAccess
    ) {
        this.id = id;
        this.authorizationCode = authorizationCode;
        this.enableOpenId = enableOpenId;
        this.enableOfflineAccess = enableOfflineAccess;
    }

    public static AuthorizationInformation of(
        final Id id,
        final AuthorizationCode authorizationCode,
        final EnableOpenId enableOpenId,
        final EnableOfflineAccess enableOfflineAccess
    ) {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(
            id,
            authorizationCode,
            enableOpenId, 
            enableOfflineAccess
        );

        return authorizationInformation;
    }
}
