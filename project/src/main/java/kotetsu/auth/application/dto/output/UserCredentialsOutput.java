package kotetsu.auth.application.dto.output;

import lombok.Getter;
import lombok.Value;

@Value
public class UserCredentialsOutput {
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

    private UserCredentialsOutput(
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

    public static UserCredentialsOutput of(        
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword,
        final boolean isActive
    ) {
        return new UserCredentialsOutput(
            key,
            name,
            imageUrl,
            email,
            hashedPassword,
            isActive
        );
    }
}
