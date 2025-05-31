package kotetsu.auth.application.domain.repository;

import kotetsu.auth.application.domain.entity.UserProfile;
import kotetsu.auth.application.domain.value.Email;

public interface IFetchUserProfileByEmailRepository {
    UserProfile fetchByEmail(Email email);
}
