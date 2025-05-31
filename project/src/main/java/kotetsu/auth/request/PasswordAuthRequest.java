package kotetsu.auth.request;

import lombok.Data;

@Data
public class PasswordAuthRequest {
    private String email;
    private String password;
}
