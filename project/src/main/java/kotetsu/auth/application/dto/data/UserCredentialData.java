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

    @Getter
    private final boolean isActive;

    private UserCredentialData(
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword,
        final boolean isActive
    ) {
        this.key = key;
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.isActive = isActive;
    }

    public static UserCredentialData of(
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword,
        final boolean isActive        
    ) {
        return new UserCredentialData(
            key,
            name,
            imageUrl,
            email,
            hashedPassword,
            isActive
        );
    }
}
