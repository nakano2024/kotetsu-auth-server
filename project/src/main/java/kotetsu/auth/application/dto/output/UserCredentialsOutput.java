package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class UserCredentialsOutput {
    @Getter
    private final String email;

    @Getter
    private final String hashedPassword;

    private UserCredentialsOutput(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public static UserCredentialsOutput of(String email, String hashedPassword) {
        return new UserCredentialsOutput(email, hashedPassword);
    }
}
