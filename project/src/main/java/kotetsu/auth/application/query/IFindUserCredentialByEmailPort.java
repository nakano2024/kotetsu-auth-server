package kotetsu.auth.application.query;

import java.util.Optional;

import kotetsu.auth.application.dto.data.UserCredentialData;

public interface IFindUserCredentialByEmailPort {
    Optional<UserCredentialData> findByEmail(String email);
}
