package kotetsu.auth.application.dto.output;

import java.util.UUID;

import lombok.Getter;

public class UserProfileOutput {
    @Getter
    private final UUID code;

    @Getter
    private final String name;

    @Getter
    private final String email;

    @Getter
    private final String imageUrl;

    private UserProfileOutput(final UUID code, final String name, final String email, final String imageUrl) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static UserProfileOutput of(final UUID code, final String name, final String email, final String imageUrl) {
        return new UserProfileOutput(code, name, email, imageUrl);
    }
}
