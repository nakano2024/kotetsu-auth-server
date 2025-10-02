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

    private UserCredentialsOutput(
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

    public static UserCredentialsOutput of(        
        final String key,
        final String name,
        final String imageUrl,
        final String email,
        final String hashedPassword
    ) {
        return new UserCredentialsOutput(
            key,
            name,
            imageUrl,
            email,
            hashedPassword
        );
    }
}
