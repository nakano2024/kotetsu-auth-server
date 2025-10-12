package kotetsu.auth.application.domain.value;

import lombok.Getter;

public class IdTokenProfile {
    @Getter
    private final UserName name;
    @Getter
    private final Email email;
    @Getter
    private final ImageUrl imageUrl;

    private IdTokenProfile(final UserName name, final Email email, final ImageUrl imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static IdTokenProfile of(final UserName name, final Email email, final ImageUrl imageUrl) {
        final IdTokenProfile idTokenProfile = new IdTokenProfile(name, email, imageUrl);
        return idTokenProfile;
    }
}
