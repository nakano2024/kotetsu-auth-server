package kotetsu.auth.dto.request;

import lombok.Data;

@Data
public class PasswordAuthRequest {
    private String email;
    private String password;
}
