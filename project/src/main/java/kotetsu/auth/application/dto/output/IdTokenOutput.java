package kotetsu.auth.application.dto.output;

import lombok.Getter;

public class IdTokenOutput {
    @Getter
    private final String idToken;

    @Getter
    private final String tokenType;

    @Getter
    private final Long expiresIn; 

    private IdTokenOutput(final String idToken, final String tokenType, final Long expiresIn) {
        this.idToken = idToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public static IdTokenOutput of(final String idToken, final String tokenType, final Long expiresIn) {
        return new IdTokenOutput(idToken, tokenType, expiresIn);
    }
}
