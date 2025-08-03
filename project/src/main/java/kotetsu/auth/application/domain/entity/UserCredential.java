package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.HashedPassword;
import kotetsu.auth.application.domain.value.Id;

public class UserCredential {
    Id id;
    Email email;
    HashedPassword password;
    
}
