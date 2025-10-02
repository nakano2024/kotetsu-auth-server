package kotetsu.auth.application.dto.data;

import lombok.Getter;

public class UserCredentialData {
    @Getter
    private final String key;

    @Getter
    private final String name;

    @Getter
    private final String imageUrl;

    @Getter
    private final String email;

    @Getter
    private final String hashedPassword;

    private UserCredentialData(
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword
    ) {
        this.key = key;
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public static UserCredentialData of(
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword        
    ) {
        return new UserCredentialData(
            key,
            name,
            imageUrl,
            email,
            hashedPassword
        );
    }
}
