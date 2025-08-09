package kotetsu.auth.application.query;

import kotetsu.auth.application.dto.data.UserCredentialData;

public interface IFindUserCredentialByEmailPort {
    UserCredentialData findByEmail(String email);
}
