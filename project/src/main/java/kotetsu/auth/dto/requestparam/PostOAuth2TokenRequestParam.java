
package kotetsu.auth.dto.requestparam;

import java.beans.ConstructorProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostOAuth2TokenRequestParam {
    @NotBlank
    private final String grantType;

    private final String code;

    private final String codeVerifier;

    private final String refreshToken;

    @ConstructorProperties({
        "grant_type",
        "code",
        "code_verifier",
        "refresh_token"
    })
    public PostOAuth2TokenRequestParam(
        final String grantType,
        final String code,
        final String codeVerifier,
        final String refreshToken
    ) {
        this.grantType = grantType;
        this.code = code;
        this.codeVerifier = codeVerifier;
        this.refreshToken = refreshToken;
    }
}
