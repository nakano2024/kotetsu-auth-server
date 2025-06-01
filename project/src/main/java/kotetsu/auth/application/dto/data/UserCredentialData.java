package kotetsu.auth.application.dto.data;

import lombok.Getter;

public class UserCredentialData {
    @Getter
    private final String email;

    @Getter
    private final String hashedPassword;

    private UserCredentialData(final String email, final String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public static UserCredentialData of(final String email, final String hashedPassword) {
        return new UserCredentialData(
            email,
            hashedPassword
        );
    }
}
