package kotetsu.auth.application.persistence;

import kotetsu.auth.application.dto.data.UserProfileData;

public interface IFindUserProfileByEmailPort {
    UserProfileData findByEmail(String email);
}
