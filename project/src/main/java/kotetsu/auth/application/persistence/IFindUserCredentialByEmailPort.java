package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.data.UserCredentialData;

public interface IFindUserCredentialByEmailPort {
    UserCredentialData findByEmail(String email);
}
