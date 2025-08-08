package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.HashedPassword;
import kotetsu.auth.application.domain.value.Key;

public class UserCredential {
    Key id;
    Email email;
    HashedPassword password;
    
}
