package kotetsu.auth.application.dto;

import lombok.Getter;

public class GetUserProfileEmailInput {
    @Getter
    private final String email;

    private GetUserProfileEmailInput(String email) {
        this.email = email;
    }

    public static GetUserProfileEmailInput of(String email) {
        return new GetUserProfileEmailInput(email);
    }
}
