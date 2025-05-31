package kotetsu.auth.application.dto;

import lombok.Getter;

public class UserProfileOutput {
    @Getter
    private final String code;

    @Getter
    private final String name;

    @Getter
    private final String email;

    @Getter
    private final String imageUrl;

    private UserProfileOutput(final String code, final String name, final String email, final String imageUrl) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static UserProfileOutput of(final String code, final String name, final String email, final String imageUrl) {
        return new UserProfileOutput(code, name, email, imageUrl);
    }
}
