package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.data.AuthorizationCodeData;

public interface IFindAuthorizationCodeByValuePort {
    AuthorizationCodeData findByValue(final String code);
}