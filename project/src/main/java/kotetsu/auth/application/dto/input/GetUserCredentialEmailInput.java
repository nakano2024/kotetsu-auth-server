package kotetsu.auth.application.dto.input;

import lombok.Getter;

public class GetUserCredentialEmailInput {
    @Getter
    private final String email;

    private GetUserCredentialEmailInput(String email) {
        this.email = email;
    }

    public static GetUserCredentialEmailInput of(String email) {
        return new GetUserCredentialEmailInput(email);
    }
}
