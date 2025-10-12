package kotetsu.auth.application.domain.entity;

import kotetsu.auth.application.domain.value.Email;
import kotetsu.auth.application.domain.value.ImageUrl;
import kotetsu.auth.application.domain.value.Key;
import kotetsu.auth.application.domain.value.UserName;
import lombok.Getter;

public class MeProfile {
    @Getter
    private final Key key;

    @Getter
    private final UserName name;

    @Getter
    private final Email email;

    @Getter
    private final ImageUrl imageUrl;
 
    private MeProfile(final Key key, final UserName name, final Email email, final ImageUrl imageUrl) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static MeProfile of(final Key key, final UserName name, final Email email, final ImageUrl imageUrl) {
        final MeProfile meProfile = new MeProfile(key, name, email, imageUrl);
        return meProfile;
    }
}
