package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class IdTokenProfile {
    @Getter
    private final UserName userName;
    @Getter
    private final Email email;
    @Getter
    private final ImageUrl imageUrl;

    private IdTokenProfile(final UserName userName, final Email email, final ImageUrl imageUrl) {
        this.userName = userName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static IdTokenProfile of(final UserName userName, final Email email, final ImageUrl imageUrl) {
        final IdTokenProfile idTokenProfile = new IdTokenProfile(userName, email, imageUrl);
        return idTokenProfile;
    }
}
