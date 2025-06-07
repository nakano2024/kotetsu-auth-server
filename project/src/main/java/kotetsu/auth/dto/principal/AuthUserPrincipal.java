package kotetsu.auth.dto.principal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthUserPrincipal {
    @Getter
    private final String subject;
}
