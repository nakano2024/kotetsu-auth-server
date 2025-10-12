package kotetsu.auth.dto.requestparam;

import java.beans.ConstructorProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostOAuth2IntrospectRequestParam {
    @NotBlank
    private final String token;

    @ConstructorProperties({"token"})
    public PostOAuth2IntrospectRequestParam(final String token) {
        this.token = token;
    }
}
