package kotetsu.auth.application.dto;

import lombok.Getter;

public class UserCredentialsOutput {
    @Getter
    private final String code;

    @Getter
    private final String email;

    @Getter
    private final String hashedPassword;

    private UserCredentialsOutput(String code, String email, String hashedPassword) {
        this.code = code;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public static UserCredentialsOutput of(String code, String email, String hashedPassword) {
        return new UserCredentialsOutput(code, email, hashedPassword);
    }
}
